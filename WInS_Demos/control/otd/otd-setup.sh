#!/bin/sh

export CLOUDLOGIC_PORT="7001"
export EXAMPLE_DOMAIN="weblogic-examples.com"
export EXAMPLE_HOST="wins-vbox.${EXAMPLE_DOMAIN}"

# -=-=-=-=- OTD Provider Params -=-=-=-=-

export OTD_MGMT_PORT="8989"
export OTD_ORIGIN_SERVER="${EXAMPLE_HOST}"
export OTD_ORIGIN_PORT="28080"
export OTD_HTTP_PORT="1905"

export OTD_BASE_DOMAIN="${EXAMPLE_DOMAIN}"
export OTD_BASE_DOMAIN="${EXAMPLE_DOMAIN}"
export OTD_HOST="172.16.254.254"
export OTD_SSH_KEY="/home/oracle/.ssh/id_rsa"
export OTD_MACH_USER="oracle"
export OTD_ADMIN_PASSWD="welcome1"
export OTD_CONFIG_NAME="otdvbox"
export OTD_INSTALL_DIR="/u01/app/oracle/product/11.1.1/trafficdirector_Home_1"
export OTD_INSTANCE_HOME="${OTD_INSTALL_DIR}/otd-wins"
export OTD_PASSWD_FILE="${OTD_INSTALL_DIR}/passwd.txt"
export OTD_HEALTH_INTERVAL="50"
export OTD_HEALTH_URL="/"
export OTD_HEALTH_TIMEOUT="25"
export OTD_E2E_SSL="false"
export OTD_DEFAULT_ORIG_SVR_POOL="origin-server-pool-1"

service_name="Oracle Traffic Director 11.1.1.7.0"
logger="/usr/bin/logger -t rc.local "

echo "Setting up OTD..."

echo "tadm_password=welcome1" > ${OTD_PASSWD_FILE}
echo "tadm_admin_password=welcome1" >> ${OTD_PASSWD_FILE}

OTD_BASE_CONFIG_OPTS="--user=admin"
OTD_BASE_CONFIG_OPTS="${OTD_BASE_CONFIG_OPTS} --password-file=${OTD_PASSWD_FILE}"

OTD_CONFIG_OPTS="${OTD_BASE_CONFIG_OPTS}"
OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --port=${OTD_MGMT_PORT}"
OTD_INSTANCE_HOME="${OTD_INSTALL_DIR}/otd-cloudlogic"

if [ -d ${OTD_INSTANCE_HOME}/admin-server ]; then
	
	read -p "OTD Configuration exists.  Reconfigure? [Y/n]? " reconfigure_otd
	
	if [ "${reconfigure_otd}" != "y" ]; then
		if [ -n "${reconfigure_otd}" ]; then
			echo "Not reconfiguring..."
			exit 1
		fi
	fi
	
	echo "OTD Admin Server exists.  De-configuring first..."
	./demo-otd-teardown.sh	
fi

OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --instance-home=${OTD_INSTANCE_HOME}"

echo "${OTD_INSTALL_DIR}/bin/tadm configure-server ${OTD_CONFIG_OPTS}"
${OTD_INSTALL_DIR}/bin/tadm configure-server ${OTD_CONFIG_OPTS}

if [ $? != 0 ]; then
	echo "Failed to configure OTD Server!"
	exit $?
fi

echo ${OTD_INSTANCE_HOME}/admin-server/bin/startserv
${OTD_INSTANCE_HOME}/admin-server/bin/startserv

if [ $? != 0 ]; then
	echo "Failed to Start OTD Server!"
	exit $?
fi

OTD_CONFIG_OPTS="${OTD_BASE_CONFIG_OPTS}"
OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --port=${OTD_MGMT_PORT}"
OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --http-port=${OTD_HTTP_PORT}"
OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --server-name=${OTD_HOST}"
OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --origin-server=${OTD_ORIGIN_SERVER}:${OTD_ORIGIN_PORT}"

echo ${OTD_INSTALL_DIR}/bin/tadm create-config ${OTD_CONFIG_OPTS} ${OTD_CONFIG_NAME}
sudo ${OTD_INSTALL_DIR}/bin/tadm create-config ${OTD_CONFIG_OPTS} ${OTD_CONFIG_NAME}

if [ $? != 0 ]; then
	echo "Failed to create OTD Configuration!"
	exit $?
fi

OTD_CONFIG_OPTS="${OTD_BASE_CONFIG_OPTS}"
OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --port=${OTD_MGMT_PORT}"
OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --config=${OTD_CONFIG_NAME}"


echo ${OTD_INSTALL_DIR}/bin/tadm create-instance ${OTD_CONFIG_OPTS} ${OTD_HOST}
${OTD_INSTALL_DIR}/bin/tadm create-instance ${OTD_CONFIG_OPTS} ${OTD_HOST}

if [ $? != 0 ]; then
	echo "Failed to Create OTD Instance!"
	exit $?
fi

${OTD_INSTALL_DIR}/bin/tadm start-instance ${OTD_CONFIG_OPTS} ${OTD_HOST}

if [ $? != 0 ]; then
	echo "Failed to Start OTD Instance!"
	exit $?
fi
