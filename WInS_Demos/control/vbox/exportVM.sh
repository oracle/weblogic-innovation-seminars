#!/bin/sh

start_date=`date +%s`

ZIP_CMD=/usr/bin/zip
PROMPT=true
EXPORT_VBOX=true
ZIP_OVA=true
OVA_VERSION="12.1.2-v5"
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

VBOX_NAME="${1}"
VBOX_OUTPUT_NAME="wins-${OVA_VERSION}.ova"
VBOX_OUTPUT_DIR="$2"
ZIP_OUTPUT_DIR="${3-$VBOX_OUTPUT_DIR}"

parse_control_settings $*

echo "Exporting VM=[$VBOX_NAME] to OVA=[$VBOX_OUTPUT_NAME] in directory=[$VBOX_OUTPUT_DIR]"
echo "Zip output dir=[$ZIP_OUTPUT_DIR]"

if [ "${PROMPT}" != false ]; then

	read -p "Continue? [y/N]" inputRead

	if [ "${inputRead}" != "y" ]; then
		exit 1
	fi
fi


mkdir -p ${VBOX_OUTPUT_DIR}
mkdir -p ${ZIP_OUTPUT_DIR}

if [ "${EXPORT_VBOX}" != "false" ];
then

  if [ -f ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME} ];
  then
    echo "Removing existing file: ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}"
    rm -v ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}
  fi

  EXPORT_CMD="VBoxManage export ${VBOX_NAME} "
  EXPORT_CMD=" ${EXPORT_CMD} --output ${VBOX_OUTPUT_DIR}/${VBOX_OUTPUT_NAME}"
  EXPORT_CMD=" ${EXPORT_CMD} --vsys 0 "
  EXPORT_CMD=" ${EXPORT_CMD} --product ${OVA_PRODUCT_NAME}"
  EXPORT_CMD=" ${EXPORT_CMD} --producturl ${OVA_PRODUCT_URL}"
  EXPORT_CMD=" ${EXPORT_CMD} --version ${OVA_VERSION}"
  EXPORT_CMD=" ${EXPORT_CMD} --eulafile ${OVA_EULA_FILE}"

  echo "Exporting VBOX=[${VBOX_NAME}] with command: ${EXPORT_CMD}"
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
