#!/bin/sh

echo "${0} | CONTROL_DIR=${CONTROL_DIR}"

if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable!"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi

. ${CONTROL_DIR}/bin/winsEnv.sh

cat ${CONTROL_DIR}/install/responses/fdisk_responses.txt | fdisk /dev/sdb

mkfs /dev/sdb1

mount -a