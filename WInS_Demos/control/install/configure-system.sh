#!/bin/sh

SOFTWARE_SOURCE="/software/oracle"
ORA_INVENTORY="/etc/oraInst.loc"
JAVA_HOME="/usr/java/latest"
JAVA="${JAVA_HOME}/bin/java"
#CONTROL_DIR="/u01/content/weblogic-innovation-seminars/WInS_Demos/control"
CONTROL_DIR="/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control"
RESPONSES_SOURCE="${CONTROL_DIR}/install/responses"

sudo hostname wins-vbox.localdomain
sudo cp ${CONTROL_DIR}/system/etc/hosts /etc/hosts
sudo cp ${CONTROL_DIR}/system/etc/sysconfig/network-scripts/ifcfg-eth6 /etc/sysconfig/network-scripts/ifcfg-eth6
sudo cp ${CONTROL_DIR}/system/etc/sysconfig/network /etc/sysconfig/network

touch /home/oracle/setProxy.sh
chmod +x /home/oracle/setProxy.sh

sudo service network restart