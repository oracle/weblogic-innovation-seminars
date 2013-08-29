#!/bin/sh

echo "${0} | CONTROL_DIR=${CONTROL_DIR}"


if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable! currently=[${CONTROL_DIR}]"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi

# HOSTNAME
sudo hostname wins-vbox.localdomain
sudo sysctl kernel.hostname=wins-vbox.localdomain
sudo cp ${CONTROL_DIR}/system/etc/hosts /etc/hosts
sudo cp ${CONTROL_DIR}/system/etc/hostname /etc/hostname
sudo cp ${CONTROL_DIR}/system/etc/sysconfig/network /etc/sysconfig/network

touch /home/oracle/setProxy.sh
chmod +x /home/oracle/setProxy.sh

sudo service network restart

check_network