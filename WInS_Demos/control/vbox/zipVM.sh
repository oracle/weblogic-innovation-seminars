#!/bin/bash

start_date=`date +%s`

ZIP_CMD=/usr/bin/zip

OVA_FILE="${1}"
ZIP_OUTPUT_DIR="${4}"

OVA_MD5_FILE=${OVA_FILE}.md5

echo "Zipping OVA=[${OVA_FILE}] and MD5=[{$OVA_MD5_FILE}] into dir=[$ZIP_OUTPUT_DIR]"

mkdir -p ${ZIP_OUTPUT_DIR}

ZIP_CMD_LINE=" ${ZIP_CMD} -T -j -s 1g -0 ${ZIP_OUTPUT_DIR}/${OVA_FILE}.zip ${OVA_FILE} ${OVA_MD5_FILE}"
echo "Zipping VBOX with command: ${ZIP_CMD_LINE}"
${ZIP_CMD_LINE}

end_date=`date +%s`
duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)

echo "WInS VBOX ZIP complete in ${duration} minutes"
