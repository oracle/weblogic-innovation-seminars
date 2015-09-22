#!/bin/bash

. /u01/content/weblogic-innovation-seminars/WInS_Demos/control/bin/winsEnv.sh
$ORACLE_HOME/bin/dbstart $ORACLE_HOME


sqlplus sys/welcome1  as sysdba <<EOF
 alter pluggable database pdb2 open;
 alter pluggable database pdb3 open;
 exit;
EOF

