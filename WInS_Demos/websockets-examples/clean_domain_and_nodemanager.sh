#!/bin/sh
#echo "Removind weblogic processes"

ps aux | grep -i weblogic | awk {'print $2'} | xargs kill -9
rm -Rf /u01/wls1212/user_projects/domains/weblogic-examples-domain
