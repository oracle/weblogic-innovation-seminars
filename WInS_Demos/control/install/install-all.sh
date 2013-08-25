#!/bin/sh

# ﻿/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control/install/

#CONTROL_DIR="/u01/content/weblogic-innovation-seminars/WInS_Demos/control"
WINS_DIR="/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/"
CONTROL_DIR="${WINS_DIR}/control"

. ${CONTROL_DIR}/bin/winsEnv.sh

sudo chown -R oracle:oinstall /u01

${CONTROL_DIR}/install/configure-system.sh

${CONTROL_DIR}/install/install-code.sh

if [ "$?" != "0" ];
then
	echo "Error installing WInS Demos!"
	exit 1
fi

${CONTROL_DIR}/install/install-third-party.sh

if [ "$?" != "0" ];
then
	echo "Error installing Third Party Software!"
	exit 1
fi

${CONTROL_DIR}/install/install-oracle.sh

if [ "$?" != "0" ];
then
	echo "Error installing Oracle Products!"
	exit 1
fi

${CONTROL_DIR}/install/install-desktop-links.sh

if [ "$?" != "0" ];
then
	echo "Error installing Desktop Links!"
	exit 1
fi


# mvn verify
# $DEMOS_HOME/initialize.sh