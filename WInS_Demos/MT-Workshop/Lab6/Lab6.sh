#!/bin/bash

/u01/wins/wls1221/oracle_common/common/bin/unpack.sh -template /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab6/otd_domain.jar -domain /u01/wins/wls1221/user_projects/domains/otd_domain -user_name=weblogic -password=welcome1 -log=otd_domain.log

cd /u01/wins/wls1221/user_projects/domains/otd_domain/bin
nohup ./startNodeManager.sh &>nmserver.log </dev/null  &
nohup ./startWebLogic.sh &>adminserver.log </dev/null  &

echo "Starting  Admin Server in otd_domain"
sleep 180

cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab6/

/u01/wins/wls1221/oracle_common/common/bin/wlst.sh otd-domain.py
