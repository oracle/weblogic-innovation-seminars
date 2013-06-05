#!/bin/bash

INSTALLER_DIR="/home/oracle/Downloads"
RESPONSES="/u01/content/weblogic-innovation-seminars/WInS_Demos/control/responses/otd.rsp"
ORA_INVENTORY="/u01/app/oraInventory/oraInst.loc"

cd $INSTALLER_DIR

./runInstaller -silent -responseFile ${RESPONSES} -waitforcompletion -invPtrLoc $ORA_INVENTORY

