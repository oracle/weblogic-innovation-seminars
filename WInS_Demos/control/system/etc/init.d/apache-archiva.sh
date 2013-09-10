#!/bin/sh
# chkconfig: 345 98 10
# description: Oracle auto start-stop script.
#
# Set ORA_OWNER to the user id of the owner of the
# Oracle database software.

export ORACLE_OWNER=oracle
export ARCHIVA_HOME="/u01/third-party/apache-archiva-1.3.6"

. /etc/rc.d/init.d/functions

echo

service_name="Apache Archiva"
logger="/usr/bin/logger -t rc.local "

case "$1" in
    start)
				echo -n "Starting ${service_name}: "
        su $ORACLE_OWNER -c "${ARCHIVA_HOME}/bin/archiva $1"

        RETVAL=$?
				if [ ${RETVAL} == "0" ]; then
					success
			    $logger "Start ${service_name} succeeded"
				else failure
			    $logger "error: Start ${service_name} failed with exit code ${RETVAL}"
				fi
				echo
        ;;

    stop)
				echo -n "Stopping ${service_name}: "
        su $ORACLE_OWNER -c "${ARCHIVA_HOME}/bin/archiva $1"
        RETVAL=$?
				if [ ${RETVAL} == "0" ]; then
					success
			    $logger "Stop ${service_name} succeeded"
				else failure
			    $logger "error: Stop ${service_name} failed with exit code ${RETVAL}"
				fi
				echo
        ;;

     *)
      su $ORACLE_OWNER -c "${ARCHIVA_HOME}/bin/archiva $1"
      ;;

esac


