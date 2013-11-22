#!/bin/sh
# chkconfig: 345 98 10
# description: Oracle auto start-stop script.
#
# Set ORA_OWNER to the user id of the owner of the
# Oracle database software.

export TMP=/tmp
export TMPDIR=$TMP
export ORACLE_HOSTNAME=wins-vbox
export ORACLE_UNQNAME=orcl
export ORACLE_SID=orcl
export ORACLE_BASE=/u01/app/oracle
export ORACLE_HOME=$ORACLE_BASE/product/12.1/database
export PATH=/usr/sbin:$ORACLE_HOME/bin:$PATH
export ORACLE_OWNER=oracle

$ORACLE_HOME/bin/sqlplus 'sys/welcome1 as sysdba' <<EOF

alter pluggable database pdb2 open;

exit
EOF

if [ "$1" == "wait" ]; then

  echo "This window will close automatically in 3s..."

  sleep 3

 fi
