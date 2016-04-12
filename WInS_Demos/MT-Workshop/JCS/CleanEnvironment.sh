#!/bin/bash

echo "********************* Stop Servers and Node Manager ***************************"
cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/JCS
pkill -f weblogic.NodeManager
pkill -f weblogic.Server


sleep 15

echo "**********************Removing Domains ****************************************"
rm -Rf /u01/wins/wls1221/user_projects/domains/base_domain
rm -Rf /u01/wins/wls1221/user_projects/applications/base_domain
rm /home/oracle/Desktop/dp1.zip
rm /home/oracle/Desktop/dp1-attributes.json

echo "********************** Removing user and table in database ***********************"
sqlplus system/welcome1@localhost:1521/pdborcl <<EOF
 drop user conference cascade;
 exit;
EOF

echo "************************** Stopping Oracle Database ********************************"
sqlplus sys/welcome1  as sysdba <<EOF
 alter pluggable database pdb2 close;
 exit;
EOF

. /u01/content/weblogic-innovation-seminars/WInS_Demos/control/bin/winsEnv.sh
$ORACLE_HOME/bin/dbshut $ORACLE_HOME
