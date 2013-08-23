#!/bin/sh

set -x

SOFTWARE_SOURCE="/software/oracle"
ORA_INVENTORY="/etc/oraInst.loc"
JAVA_HOME="/usr/java/latest"
JAVA="${JAVA_HOME}/bin/java"
#CONTROL_DIR="/u01/content/weblogic-innovation-seminars/WInS_Demos/control"
CONTROL_DIR="/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control"
RESPONSES_SOURCE="${CONTROL_DIR}/install/responses"

# list files look and link to desktop
for FILE in `ls -1 ${CONTROL_DIR}/home/oracle/Desktop/*.desktop`
do
 FILE=`basename $FILE`
 echo "Processing $FILE"
 sudo ln -s ${CONTROL_DIR}/home/oracle/Desktop/$FILE ~oracle/Desktop/$FILE
done


