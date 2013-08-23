#!/bin/sh

set -x

CONTROL_DIR="/u01/content/weblogic-innovation-seminars/WInS_Demos/control"

# list files look and link to desktop
for FILE in `ls ${CONTROL_DIR}/desktop/*.desktop`
do
 echo "Processing $FILE"
 sudo ln -s ${CONTROL_DIR}/control/desktop/$FILE ~oracle/Desktop/$FILE
done


