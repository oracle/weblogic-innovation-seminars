#!/bin/bash

cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab1/
echo “*****************STARTING DB**********************************”
./startDB.sh wait

echo “*****************CREATING DB ENTRIES FOR PetStore Application ******************”
cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8
./petstoreDB.sh

export JAVA_HOME=/usr/java/jdk1.7.0_79/
export MW_HOME=/u01/wins/wls1036/

echo “***************** STARTING ADMIN SERVER IN WEBLOGIC 10.3.6 DOMAIN DOMAIN1036 ******************”
cd /u01/wins/wls1036/user_projects/domains/Domain1036/bin
nohup ./startWebLogic.sh &>adminserver.log </dev/null  &
sleep 90 

export JAVA_HOME=/usr/java/latest/
export MW_HOME=/u01/wins/wls1221/

echo “***************** STARTING ADMIN SERVER IN WEBLOGIC 12.2.1 DOMAIN DOMAIN1221 ******************”
cd /u01/wins/wls1221/user_projects/domains/Domain1221/bin
nohup ./startWebLogic.sh &>adminserver.log </dev/null  &
sleep 90

