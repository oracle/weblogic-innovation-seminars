#!/bin/bash

/u01/wins/wls1221/oracle_common/common/bin/unpack.sh -template /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab4/dev-domain.jar -domain /u01/wins/wls1221/user_projects/domains/dev_domain -user_name=weblogic -password=welcome1 -log=dev_domain.log

cd /u01/wins/wls1221/user_projects/domains/dev_domain/bin
nohup ./startWebLogic.sh &>adminserver.log </dev/null  &

echo "Starting Admin Server in dev_domain"
sleep 90

cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab4

/u01/wins/wls1221/oracle_common/common/bin/wlst.sh Lab4.py


