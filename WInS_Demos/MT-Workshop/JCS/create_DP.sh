#!/bin/bash

cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/JCS/
./startDB.sh wait
./conferenceDB.sh


/u01/wins/wls1221/oracle_common/common/bin/unpack.sh -template /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/JCS/base_domain.jar -domain /u01/wins/wls1221/user_projects/domains/base_domain -user_name=weblogic -password=welcome1 -log=base_domain.log

cd /u01/wins/wls1221/user_projects/domains/base_domain/bin
nohup ./startNodeManager.sh &>nmserver.log </dev/null  &
#nohup ./startWebLogic.sh &>adminserver.log </dev/null  &

echo "Starting WebLogic Admin Server"
sleep 10

cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/JCS/
/u01/wins/wls1221/oracle_common/common/bin/wlst.sh create_DP.py



 

