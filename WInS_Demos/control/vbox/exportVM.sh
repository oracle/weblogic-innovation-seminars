#!/bin/sh

start_date=`date +%s`

ZIP_CMD=/usr/bin/zip
PROMPT=true
EXPORT_VBOX=true
ZIP_OVA=true
OVA_VERSION="12.1.2-v6"
OVA_PRODUCT_NAME="WInS-VirtualBox-VM-${OVA_VERSION}"
OVA_PRODUCT_URL="http://retriever.us.oracle.com/apex/f?p=121:3:2027314285611264::NO:RP:P3_PAGE_ID:2129"
OVA_EULA_FILE="/Users/jeffreyawest/Data/mycode/github/oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control/vbox/eula.txt"

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
		exit 1
	fi
fi


VBoxManage list runningvms |grep ${VBOX_NAME_INPUT}
if [ "$?" == "0" ]; then
  VBoxManage guestcontrol ${VBOX_NAME_INPUT} execute --image /u01/content/weblogic-innovation-seminars/WInS_Demos/control/bin/prepareForExport.sh --username oracle --password welcome1 --wait-stdout --wait-stderr

  VBoxManage controlvm ${VBOX_NAME_INPUT} acpipowerbutton
fi


# rename so new version is in the list of VMs
VBoxManage modifyvm ${VBOX_NAME_INPUT} --name "${VBOX_NAME_NEW}"

# update the network for the non-internal NIC to NAT
VBoxManage modifyvm ${VBOX_NAME_NEW} --nic1 nat

# set CPU and memory to a reasonable amount
VBoxManage modifyvm ${VBOX_NAME_NEW} --memory 6144
VBoxManage modifyvm ${VBOX_NAME_NEW} --cpus 4

# remove USB so you don't need the USB extensions
VBoxManage modifyvm ${VBOX_NAME_NEW} --usb off

# remove shared folders
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
