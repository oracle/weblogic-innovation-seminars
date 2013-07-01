#!/bin/sh
# chkconfig: 345 99 10
# description: Oracle auto start-stop script.
#
# Set ORA_OWNER to the user id of the owner of the
# Oracle software.

export TMP=/tmp
export TMPDIR=$TMP
export ORACLE_OWNER=oracle

. /etc/rc.d/init.d/functions

export CLOUDLOGIC_DOMAIN="weblogic-examples.com"
export CLOUDLOGIC_HOST="wins-vbox.${CLOUDLOGIC_DOMAIN}"

export OTD_MGMT_PORT="8989"
export OTD_ORIGIN_SERVER="${CLOUDLOGIC_HOST}"
export OTD_ORIGIN_PORT="28080"
export OTD_HTTP_PORT="1905"

export OTD_BASE_DOMAIN="${CLOUDLOGIC_DOMAIN}"
export OTD_HOST="192.168.10.3"
export OTD_SSH_KEY="/home/oracle/.ssh/id_rsa"
export OTD_MACH_USER="oracle"
export OTD_ADMIN_PASSWD="admin123"
export OTD_CONFIG_NAME="otdvbox"
export OTD_INSTALL_DIR="/u01/app/oracle/product/11.1.1.7.0/trafficdirector_Home_1"
export OTD_INSTANCE_HOME="${OTD_INSTALL_DIR}/otd-cloudlogic"
export OTD_PASSWD_FILE="${OTD_INSTALL_DIR}/passwd.txt"
export OTD_HEALTH_INTERVAL="50"
export OTD_HEALTH_URL="/"
export OTD_HEALTH_TIMEOUT="25"
export OTD_E2E_SSL="false"
export OTD_DEFAULT_ORIG_SVR_POOL="origin-server-pool-1"

service_name="Oracle Traffic Director 11.1.1.7.0"
logger="/usr/bin/logger -t rc.local "

start_otd()
{
	export OTD_CONFIG_OPTS="--user=admin"
	export OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --password-file=${OTD_PASSWD_FILE}"
	export OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --port=${OTD_MGMT_PORT}"

	su $ORACLE_OWNER -c "${OTD_INSTALL_DIR}/bin/tadm get-admin-prop ${OTD_CONFIG_OPTS}   >/dev/null 2>&1"

	if [ "$?" == "0" ]; then
		$logger "OTD Admin Node already running.  Exiting..."
		failure
	fi

	echo -n "Starting ${service_name}: AdminServer: "
	$logger "Starting ${service_name}: AdminServer: "

	su $ORACLE_OWNER -c "${OTD_INSTANCE_HOME}/admin-server/bin/startserv >/dev/null 2>&1"
	startserv_retval=$?

	if [ "${startserv_retval}" == "0" ]; then
		success
		echo
	else
		failure
		echo
		$logger "error: Start ${service_name} failed with exit code ${startserv_retval} on ${OTD_INSTANCE_HOME}/admin-server/bin/startserv"
		exit
	fi


	OTD_CONFIG_OPTS="--user=admin"
	OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --password-file=${OTD_PASSWD_FILE}"
	OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --port=${OTD_MGMT_PORT}"
	OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --config=${OTD_CONFIG_NAME}"

	echo -n "Starting ${service_name}: Instance: "

	su $ORACLE_OWNER -c "${OTD_INSTALL_DIR}/bin/tadm start-instance ${OTD_CONFIG_OPTS} ${OTD_HOST}   >/dev/null 2>&1"

  RETVAL=$?

	if [ ${RETVAL} == "0" ]; then
 		success
 		echo
  	$logger "Start ${service_name} succeeded"
	else
		failure
		echo
		$logger "error: Start ${service_name} failed with exit code ${RETVAL} on 	${OTD_INSTALL_DIR}/bin/tadm start-instance ${OTD_CONFIG_OPTS} ${OTD_HOST} "
	fi

	echo
}

stop_otd()
{
	echo "Stopping ${service_name}: "

	OTD_CONFIG_OPTS="--user=admin"
	OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --password-file=${OTD_PASSWD_FILE}"
	OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --port=${OTD_MGMT_PORT}"
	OTD_CONFIG_OPTS="${OTD_CONFIG_OPTS} --config=${OTD_CONFIG_NAME}"

	echo -n "Stopping ${service_name}: Instance: "

	su $ORACLE_OWNER -c "${OTD_INSTALL_DIR}/bin/tadm stop-instance ${OTD_CONFIG_OPTS} >/dev/null 2>&1"

  RETVAL=$?
	if [ ${RETVAL} == "0" ]; then
		success
		echo
	  $logger "${service_name} stop-instance succeeded"
	else
		failure
		echo
		$logger "error: Stop ${service_name} failed with exit code ${RETVAL}"
	fi

	echo -n "Stopping ${service_name}: AdminServer: "
	su $ORACLE_OWNER -c "${OTD_INSTANCE_HOME}/admin-server/bin/stopserv >/dev/null 2>&1"

  RETVAL=$?
	if [ ${RETVAL} == "0" ]; then
		success
		echo
		$logger "Stop ${service_name}:stopserv succeeded"
	else
		failure
		echo
		$logger "error: Stop ${service_name}:stopserv failed with exit code ${RETVAL}"
	fi
	echo
}

case "$1" in
    'start')
				start_otd
        ;;
    'stop')
    		stop_otd
        ;;
    'restart')
				stop_otd
				start_otd
        ;;
esac
