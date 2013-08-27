#!/bin/sh

# ﻿/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control/install/

echo "${0} / CONTROL_DIR=${CONTROL_DIR}"


if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable!"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi

. ${CONTROL_DIR}/bin/winsEnv.sh

sudo chown -R oracle:oinstall /u01

${CONTROL_DIR}/install/configure-network.sh

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

${CONTROL_DIR}/install/preload-maven.sh

if [ "$?" != "0" ];
then
	echo "Error Running Maven!"
	exit 1
fi

# mvn verify
# $DEMOS_HOME/initialize.sh