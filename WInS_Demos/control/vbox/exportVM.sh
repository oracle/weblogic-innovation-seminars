#!/bin/sh

set +e

start_date=`date +%s`

ZIP_CMD=/usr/bin/zip
PROMPT=true
EXPORT_VBOX=true
ZIP_OVA=true
OVA_VERSION="12.1.2-v6"
OVA_PRODUCT_NAME="WInS-VirtualBox-VM-${OVA_VERSION}"
OVA_PRODUCT_URL="http://retriever.us.oracle.com/apex/f?p=121:3:2027314285611264::NO:RP:P3_PAGE_ID:2129"
OVA_EULA_FILE="/Users/jeffreyawest/Data/mycode/github/oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control/vbox/eula.txt"
OVA_MEMORY_SIZE=6144
OVA_CPU_COUNT=4

parse_control_settings()
{
	for param in $*
	do
		if [ "${param}" == "-y" ]; then
			PROMPT=false
		elif [ "${param}" == "-nozip" ]; then
			ZIP_OVA=false
		elif [ "${param}" == "-noexport" ]; then
			EXPORT_VBOX=false
		fi
	done
}

set -e

VBOX_NAME_INPUT="${1}"
VBOX_NAME_NEW="wins-${OVA_VERSION}"
VBOX_OUTPUT_NAME="${VBOX_NAME_NEW}.ova"
VBOX_OUTPUT_DIR="${2}"
ZIP_OUTPUT_DIR="${3}"

parse_control_settings $*

echo "Exporting VM=[${VBOX_NAME_INPUT}] to OVA=[${VBOX_OUTPUT_NAME}] in directory=[$VBOX_OUTPUT_DIR]"
echo "Zip output dir=[$ZIP_OUTPUT_DIR]"

if [ "${PROMPT}" != false ]; then

	read -p "Continue? [y/N]" inputRead

	if [ "${inputRead}" != "y" ]; then
	  echo "Exiting..."
		exit 1
	fi

fi

echo "Proceeding..."

echo "VBoxManage list runningvms | grep ${VBOX_NAME_INPUT}"
VBoxManage list runningvms | grep ${VBOX_NAME_INPUT}

STILL_RUNNING=${?}

echo "STILL_RUNNING=$STILL_RUNNING"

if [ "$STILL_RUNNING" == "0" ];
then
  echo "VM is running, doing other things..."
  VBoxManage guestcontrol ${VBOX_NAME_INPUT} execute --image /u01/content/weblogic-innovation-seminars/WInS_Demos/control/bin/prepareForExportExternal.sh --username root --password welcome1 --wait-stdout

  VBoxManage controlvm ${VBOX_NAME_INPUT} acpipowerbutton

  VBoxManage list runningvms |grep ${VBOX_NAME_INPUT}

  STILL_RUNNING=$?
  while [ "$STILL_RUNNING" == "0" ];
  do
    echo "Sleeping 5s more to wait for VM to stop"
    sleep 5s
    VBoxManage list runningvms |grep ${VBOX_NAME_INPUT}
    STILL_RUNNING=$?
  done
fi


echo "Renaming VirtualBox from ${VBOX_NAME_INPUT} to ${VBOX_NAME_NEW}"
VBoxManage modifyvm ${VBOX_NAME_INPUT} --name "${VBOX_NAME_NEW}"

echo "Setting Network Adapter 1 of ${VBOX_NAME_NEW} to NAT"
VBoxManage modifyvm ${VBOX_NAME_NEW} --nic1 nat

echo "Setting Memory of ${VBOX_NAME_NEW} to ${OVA_MEMORY_SIZE}"
VBoxManage modifyvm ${VBOX_NAME_NEW} --memory ${OVA_MEMORY_SIZE}

echo "Setting CPUs ${VBOX_NAME_NEW} to ${OVA_MEMORY_SIZE}"
VBoxManage modifyvm ${VBOX_NAME_NEW} --cpus ${OVA_CPU_COUNT}

echo "Disabling USB on ${VBOX_NAME_NEW}"
VBoxManage modifyvm ${VBOX_NAME_NEW} --usb off

# remove shared folders
echo "Removing shared folders of ${VBOX_NAME_NEW}..."
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "jeffreyawest"
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "opt"
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "temp"
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "tmp"
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "oracle-parcel-service"
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "oracle-weblogic"
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "weblogic-innovation-seminars"

echo "ERRORS ABOVE ARE A_OK!  This simply means the shared fodlers were not mounted!"
echo "ERRORS ABOVE ARE A_OK!  This simply means the shared fodlers were not mounted!"
echo "ERRORS ABOVE ARE A_OK!  This simply means the shared fodlers were not mounted!"
echo "ERRORS ABOVE ARE A_OK!  This simply means the shared fodlers were not mounted!"
echo "ERRORS ABOVE ARE A_OK!  This simply means the shared fodlers were not mounted!"


mkdir -p ${VBOX_OUTPUT_DIR}
mkdir -p ${ZIP_OUTPUT_DIR}

if [ "${EXPORT_VBOX}" != "false" ];
then

  if [ -f ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME} ];
  then
    echo "Removing existing file: ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}"
    rm -v ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}
  fi

  EXPORT_CMD="VBoxManage export ${VBOX_NAME_NEW} "
  EXPORT_CMD=" ${EXPORT_CMD} --output ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}"
  EXPORT_CMD=" ${EXPORT_CMD} --vsys 0 "
  EXPORT_CMD=" ${EXPORT_CMD} --product ${OVA_PRODUCT_NAME}"
  EXPORT_CMD=" ${EXPORT_CMD} --producturl ${OVA_PRODUCT_URL}"
  EXPORT_CMD=" ${EXPORT_CMD} --version ${OVA_VERSION}"
  EXPORT_CMD=" ${EXPORT_CMD} --eulafile ${OVA_EULA_FILE}"

  echo "Exporting VBOX=[${VBOX_NAME_NEW}] with command: ${EXPORT_CMD}"
	${EXPORT_CMD}
fi

if [ "${ZIP_OVA}" != "false" ];
then
  mkdir -p ${ZIP_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}
	ZIP_CMD_LINE=" ${ZIP_CMD} -j -s 1g -1 ${ZIP_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}/${VBOX_OUTPUT_NAME}.zip ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}"
	echo "Zipping VBOX with command: ${ZIP_CMD_LINE}"
	${ZIP_CMD_LINE}
	rm ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}
fi

end_date=`date +%s`
duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)

echo "WInS VBOX export complete in ${duration} minutes"
