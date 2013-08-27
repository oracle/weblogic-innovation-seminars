#!/bin/sh

echo "${0} | CONTROL_DIR=${CONTROL_DIR}"


if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable!"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi

sudo mkdir -p /u01/content
sudo chown oracle:oinstall /u01/content

cd /u01/content

git clone https://github.com/oracle-weblogic/weblogic-innovation-seminars.git
git clone https://github.com/jeffreyawest/oracle-parcel-service.git
