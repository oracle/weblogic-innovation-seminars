#!/bin/bash

start_date=`date +%s`
ZIP_SCRIPT="/u01/content/weblogic-innovation-seminars/WInS_Demos/control/vbox/zipVM.sh"

VBOX_NAME_INPUT="${1}"
VBOX_NAME_NEW="${2}"
VBOX_OUTPUT_NAME="${VBOX_NAME_NEW}.ova"
VBOX_OUTPUT_DIR="${3}"
ZIP_OUTPUT_DIR="${4}"

PROMPT=true
ZIP_OVA=false
OVA_PRODUCT_NAME="WInS-VirtualBox-VM"
OVA_PRODUCT_URL="http://retriever.us.oracle.com/apex/f?p=121:3:2027314285611264::NO:RP:P3_PAGE_ID:2129"
OVA_EULA_FILE="/Users/jeffreyawest/Data/mycode/github/oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control/vbox-build/eula.txt"
OVA_MEMORY_SIZE=6144
OVA_CPU_COUNT=4
OVA_MD5_FILE=${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}.md5

continue_prompt()
{
  if [ "${PROMPT}" != false ]; then

    read -p "Continue? [y/N]" inputRead

    if [ "${inputRead}" != "y" ]; then
      echo "Exiting..."
      exit 1
    fi
  fi
}
parse_control_settings()
{
	for param in $*
	do
		if [ "${param}" == "-y" ]; then
			PROMPT=false
		elif [ "${param}" == "--zip" ]; then
			ZIP_OVA=true
		fi
	done
}

check_for_error()
{
  if [ "$1" != "0" ]; then
    echo "Error(s) encountered!"
    exit $1
   fi
}

usage()
{
  echo "Argument 1 (Required): VBOX_NAME_INPUT=${VBOX_NAME_INPUT}"
  echo "Argument 2 (Required): VBOX_NAME_NEW=${VBOX_NAME_NEW}"
  echo "Argument 3 (Required): VBOX_OUTPUT_DIR=${VBOX_OUTPUT_DIR}"
  echo "Argument 4 (Optional): ZIP_OUTPUT_DIR=${ZIP_OUTPUT_DIR}"
}
parse_control_settings $*

echo "Exporting VM=[${VBOX_NAME_INPUT}] to OVA=[${VBOX_OUTPUT_NAME}] in directory=[$VBOX_OUTPUT_DIR]"
echo "Zip output dir=[$ZIP_OUTPUT_DIR]"

usage

continue_prompt

echo "Proceeding..."
VBoxManage list runningvms | grep ${VBOX_NAME_INPUT} > /dev/null
RUNNING=$?

if [ "$RUNNING" == "0" ];
then
  echo "VM Is running!  Please stop the VM before continuing!"
  exit 1
#  echo "VM is running, doing other things..."
#  VBoxManage guestcontrol ${VBOX_NAME_INPUT} execute --image /u01/content/weblogic-innovation-seminars/WInS_Demos/control/bin/prepareForExportExternal.sh --username root --password welcome1 --wait-stdout
#
#  echo "Execution of prepare script complete!"
#
#  VBoxManage controlvm ${VBOX_NAME_INPUT} acpipowerbutton
#
#  VBoxManage list runningvms |grep ${VBOX_NAME_INPUT}
#
#  STILL_RUNNING=$?
#  while [ "$STILL_RUNNING" == "0" ];
#  do
#    echo "Sleeping 5s more to wait for VM to stop"
#    sleep 5s
#    VBoxManage list runningvms |grep ${VBOX_NAME_INPUT}
#    STILL_RUNNING=$?
#  done
fi


echo "Renaming VirtualBox from ${VBOX_NAME_INPUT} to ${VBOX_NAME_NEW}"
VBoxManage modifyvm ${VBOX_NAME_INPUT} --name "${VBOX_NAME_NEW}"
check_for_error $?

echo "Setting Network Adapter 1 of ${VBOX_NAME_NEW} to NAT"
VBoxManage modifyvm ${VBOX_NAME_NEW} --nic1 nat > /dev/null 2>&1

echo "Setting Memory of ${VBOX_NAME_NEW} to ${OVA_MEMORY_SIZE}"
VBoxManage modifyvm ${VBOX_NAME_NEW} --memory ${OVA_MEMORY_SIZE} > /dev/null 2>&1

echo "Setting CPUs ${VBOX_NAME_NEW} to ${OVA_MEMORY_SIZE}"
VBoxManage modifyvm ${VBOX_NAME_NEW} --cpus ${OVA_CPU_COUNT} > /dev/null 2>&1

echo "Disabling USB on ${VBOX_NAME_NEW}"
VBoxManage modifyvm ${VBOX_NAME_NEW} --usb off > /dev/null 2>&1

echo "Setting OS Type to Oracle_64"
VBoxManage modifyvm ${VBOX_NAME_NEW} --ostype Oracle_64 > /dev/null 2>&1

VBoxManage modifyvm ${VBOX_NAME_NEW} --clipboard bidirectional > /dev/null 2>&1

# remove shared folders
echo "Removing shared folders of ${VBOX_NAME_NEW}..."
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "jeffreyawest" > /dev/null 2>&1
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "mycode" > /dev/null 2>&1
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "opt" > /dev/null 2>&1
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "temp" > /dev/null 2>&1
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "tmp" > /dev/null 2>&1
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "oracle-parcel-service" > /dev/null 2>&1
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "oracle-weblogic" > /dev/null 2>&1
VBoxManage sharedfolder remove ${VBOX_NAME_NEW} --name "weblogic-innovation-seminars" > /dev/null 2>&1

mkdir -p ${VBOX_OUTPUT_DIR}

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
EXPORT_CMD=" ${EXPORT_CMD} --version ${VBOX_NAME_NEW}"
EXPORT_CMD=" ${EXPORT_CMD} --eulafile ${OVA_EULA_FILE}"

echo "Exporting VBOX=[${VBOX_NAME_NEW}] with command: ${EXPORT_CMD}"
${EXPORT_CMD}
check_for_error $?
md5 ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME} > ${OVA_MD5_FILE}

if [ "${ZIP_OVA}" != "false" ];
then
  ${ZIP_SCRIPT} ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME} ${ZIP_OUTPUT_DIR}
  else
  echo "--zip was not specified, bypassing zip step"
fi



end_date=`date +%s`
duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)

echo "WInS VBOX export complete in ${duration} minutes"
