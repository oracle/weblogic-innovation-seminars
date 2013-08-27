#!/bin/sh

echo "${0} / CONTROL_DIR=${CONTROL_DIR}"

if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable!"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi


set -x

# list files look and link to desktop
for FILE in `ls -1 ${CONTROL_DIR}/system/home/oracle/Desktop/*.desktop`
do
 FILE=`basename $FILE`
 echo "Processing $FILE"
 sudo ln -s ${CONTROL_DIR}/system/home/oracle/Desktop/$FILE ~oracle/Desktop/$FILE
done


