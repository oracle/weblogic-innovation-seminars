#!/bin/bash

echo "********************* Stop Servers and Node Manager ***************************"
pkill -f weblogic.NodeManager
pkill -f weblogic.Server

echo "**********************Removing Domains ****************************************"
rm -Rf /u01/wins/wls1221/user_projects/domains/base_domain
rm -Rf /u01/wins/wls1221/user_projects/domains/dev_domain
rm -Rf /u01/wins/wls1221/user_projects/domains/otd_domain
rm -Rf /u01/wins/wls1221/user_projects/applications/base_domain
rm -Rf /u01/wins/wls1221/user_projects/applications/otd_domain
rm /home/oracle/Desktop/Medrec-Dev.zip
rm /home/oracle/Desktop/Medrec-Dev-attributes.json

echo "********************** Removing user and table in database ***********************"
sqlplus system/welcome1@localhost:1521/pdborcl <<EOF
 drop user medrec1 cascade;
 drop user trade cascade;
 exit;
EOF
sqlplus system/welcome1@localhost:1521/pdb2 <<EOF
 drop user medrec2 cascade;
 exit;
EOF


echo "************************** Stopping Oracle Database ********************************"
sqlplus sys/welcome1  as sysdba <<EOF
 alter pluggable database pdb2 close;
 exit;
EOF

. /u01/content/weblogic-innovation-seminars/WInS_Demos/control/bin/winsEnv.sh
$ORACLE_HOME/bin/dbshut $ORACLE_HOME