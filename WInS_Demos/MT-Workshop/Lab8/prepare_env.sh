#!/bin/bash

echo "********** STARTING DB ************************************************************"
#./startDB.sh wait
. /u01/content/weblogic-innovation-seminars/WInS_Demos/control/bin/winsEnv.sh
$ORACLE_HOME/bin/dbstart $ORACLE_HOME

echo "********** CREATING DB ENTRIES FOR PetStore Application ***************************"
cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8
./petstoreDB.sh

export JAVA_HOME=/usr/java/jdk1.7.0_79/
export MW_HOME=/u01/wins/wls1036/

echo "********** STARTING ADMIN (WEBLOGIC 10.3.6 - DOMAIN1036) **************************"
cd /u01/wins/wls1036/user_projects/domains/Domain1036/bin
nohup ./startWebLogic.sh &>adminserver.log </dev/null  &

sleep 3

tail -f /u01/wins/wls1036/user_projects/domains/Domain1036/bin/adminserver.log | while read LOGLINE
do
   [[ "${LOGLINE}" == *"Server state changed to RUNNING"* ]] && pkill -P $$ tail
done

echo "********** ADMIN SERVER (WEBLOGIC 10.3.6 - DOMAIN1036) HAS BEEN STARTED ***********"

export JAVA_HOME=/usr/java/latest/
export MW_HOME=/u01/wins/wls1221/

echo "********** STARTING ADMIN SERVER (WEBLOGIC 12.2.1 - DOMAIN1221) *******************"
cd /u01/wins/wls1221/user_projects/domains/Domain1221/bin
nohup ./startWebLogic.sh &>adminserver.log </dev/null  &

sleep 3

tail -f /u01/wins/wls1221/user_projects/domains/Domain1221/bin/adminserver.log | while read LOGLINE
do
   [[ "${LOGLINE}" == *"Server state changed to RUNNING"* ]] && pkill -P $$ tail
done
echo "********** ADMIN SERVER (WEBLOGIC 12.2.1 - DOMAIN1221) HAS BEEN STARTED ***********"


