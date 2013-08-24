#!/bin/sh
#------------------------------------------------------------------------------
#
# Copyright (c) 2010-2009, Oracle Corporation
#
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors: 
#
#   Pascal Bleser
# 
#------------------------------------------------------------------------------

### BEGIN INIT INFO
# Provides:          hudson
# Required-Start:    $local_fs $remote_fs $network $time $named
# Should-Start: $time sendmail
# Required-Stop:     $local_fs $remote_fs $network $time $named
# Should-Stop: $time sendmail
# Default-Start:     3 5
# Default-Stop:      0 1 2 6
# Short-Description: Hudson continuous build server
# Description:       Start the Hudson continuous build server
### END INIT INFO

# Check for missing binaries (stale symlinks should not happen)
HUDSON_WAR="/usr/lib/hudson/hudson.war"
test -r "$HUDSON_WAR" || { echo "$HUDSON_WAR not installed"; 
	if [ "$1" = "stop" ]; then exit 0;
	else exit 5; fi; }

# Check for existence of needed config file and read it
HUDSON_CONFIG=/etc/sysconfig/hudson
test -e "$HUDSON_CONFIG" || { echo "$HUDSON_CONFIG not existing";
	if [ "$1" = "stop" ]; then exit 0;
	else exit 6; fi; }
test -r "$HUDSON_CONFIG" || { echo "$HUDSON_CONFIG not readable. Perhaps you forgot 'sudo'?";
	if [ "$1" = "stop" ]; then exit 0;
	else exit 6; fi; }

HUDSON_PID_FILE="/var/run/hudson.pid"
HUDSON_USER="hudson"
HUDSON_GROUP="hudson"

# Source function library.
. /etc/init.d/functions

# Read config	
[ -f "$HUDSON_CONFIG" ] && . "$HUDSON_CONFIG"

# Set up environment accordingly to the configuration settings
[ -n "$HUDSON_HOME" ] || { echo "HUDSON_HOME not configured in $HUDSON_CONFIG";
	if [ "$1" = "stop" ]; then exit 0;
	else exit 6; fi; }
[ -d "$HUDSON_HOME" ] || { echo "HUDSON_HOME directory does not exist: $HUDSON_HOME";
	if [ "$1" = "stop" ]; then exit 0;
	else exit 1; fi; }
export HUDSON_HOME

HUDSON_JAVA_CMD=/usr/java/latest/bin/java

JAVA_CMD="$HUDSON_JAVA_CMD $HUDSON_JAVA_OPTIONS -DHUDSON_HOME=$HUDSON_HOME -jar $HUDSON_WAR"
PARAMS="--logfile=/var/log/hudson/hudson.log --daemon"
[ -n "$HUDSON_PORT" ] && PARAMS="$PARAMS --httpPort=$HUDSON_PORT"
[ -n "$HUDSON_DEBUG_LEVEL" ] && PARAMS="$PARAMS --debug=$HUDSON_DEBUG_LEVEL"
[ -n "$HUDSON_HANDLER_STARTUP" ] && PARAMS="$PARAMS --handlerCountStartup=$HUDSON_HANDLER_STARTUP"
[ -n "$HUDSON_HANDLER_MAX" ] && PARAMS="$PARAMS --handlerCountMax=$HUDSON_HANDLER_MAX"
[ -n "$HUDSON_HANDLER_IDLE" ] && PARAMS="$PARAMS --handlerCountMaxIdle=$HUDSON_HANDLER_IDLE"
[ -n "$HUDSON_ARGS" ] && PARAMS="$PARAMS $HUDSON_ARGS"

if [ "$HUDSON_ENABLE_ACCESS_LOG" = "yes" ]; then
    PARAMS="$PARAMS --accessLoggerClassName=winstone.accesslog.SimpleAccessLogger --simpleAccessLogger.format=combined --simpleAccessLogger.file=/var/log/hudson/access_log"
fi

CMDLINE="${JAVA_CMD} ${PARAMS}"

RETVAL=0

case "$1" in
    start)
	echo -n "Starting Hudson "
	echo nohup su $HUDSON_USER -c "${CMDLINE}"  >/var/log/hudson/hudson.out &
	nohup su $HUDSON_USER -c "${CMDLINE}"  >/var/log/hudson/hudson.out &
	RETVAL=$?
	echo $! > $HUDSON_PID_FILE
	if [ $RETVAL = 0 ]; then
	    success
	else
	    failure
	fi
	echo
	;;
    stop)
	echo -n "Shutting down Hudson "
	killproc hudson
	RETVAL=$?
	echo
	;;
    try-restart|condrestart)
	if test "$1" = "condrestart"; then
		echo "${attn} Use try-restart ${done}(LSB)${attn} rather than condrestart ${warn}(RH)${norm}"
	fi
	$0 status
	if test $? = 0; then
		$0 restart
	else
		: # Not running is not a failure.
	fi
	;;
    restart)
	$0 stop
	$0 start
	;;
    force-reload)
	echo -n "Reload service Hudson "
	$0 try-restart
	;;
    reload)
    	$0 restart
	;;
    status)
    	status hudson
	RETVAL=$?
	;;
    probe)
	## Optional: Probe for the necessity of a reload, print out the
	## argument to this init script which is required for a reload.
	## Note: probe is not (yet) part of LSB (as of 1.9)

	test "$HUDSON_CONFIG" -nt "$HUDSON_PID_FILE" && echo reload
	;;
    *)
	echo "Usage: $0 {start|stop|status|try-restart|restart|force-reload|reload|probe}"
	exit 1
	;;
esac
exit $RETVAL
