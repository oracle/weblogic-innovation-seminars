#!/bin/sh

if [ -z "$1" ] || [ -z "$2" ]; then
  echo "usage: ${0} <db user> <db password> [<PDB name>]";
  exit -1;
fi

dbuser=${1}
dbpassword=${2}
#Default PDB to PDB1 if not specified
if [ -z "$3" ]; then
   pdb="PDB2";
else
  pdb=${3};
fi

#*************************************************************
# Test to see if Oracle database is running
#*************************************************************
check_stat=`ps -ef|grep ${ORACLE_SID}|grep pmon|wc -l`;
oracle_num=`expr ${check_stat}`
if [ $oracle_num -lt 1 ]
then
  echo "Oracle database (sid: ${ORACLE_SID}) is NOT running. Starting database first."
    
  $ORACLE_HOME/bin/dbstart $ORACLE_HOME
else
  echo "Oracle database (sid: ${ORACLE_SID}) is running."
fi


echo "Open pluggable database: ${pdb}."
sqlplus -s sys/${dbpassword} as sysdba <<EOF
set feedback off;
set serveroutput on;
declare
  pdb_counter NUMBER(1);
begin
  select count(1) into pdb_counter from v\$pdbs where name = upper('${pdb}') and open_mode != 'READ WRITE';
  if pdb_counter > 0 then
    dbms_output.put_line('${pdb} is not yet opened'); 
    EXECUTE IMMEDIATE 'alter pluggable database ${pdb} open';
  else
    dbms_output.put_line('${pdb} is already opened');
  end if;  
end;
/
exit;
EOF

sleep 3

echo "********** CREATING PetStore DB USER ***************************"

sqlplus -s system/${dbpassword}@${pdb} <<EOF
 drop user petstore cascade;

 create user petstore identified by ${dbpassword};
 grant DBA to petstore;
 exit;
EOF

echo "********** CREATING DB ENTRIES FOR PetStore Application ***************************"

sqlplus petstore/${dbpassword}@${pdb} <<EOF
 @/u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8/petstore.sql
 exit;
EOF

export JAVA_HOME=/usr/java/jdk1.7.0_79
export PATH=$JAVA_HOME/bin:$PATH
export MW_HOME=/u01/wins/wls1036

echo "********** CREATING PETSTORE_DOMAIN (WEBLOGIC 10.3.6 - PETSTORE_DOMAIN) **************************"
rm -rf /u01/wins/wls1036/user_projects/domains/petstore_domain

/u01/wins/wls1036/wlserver_10.3/common/bin/unpack.sh -template=/u01/content/weblogic-innovation-seminars/cloud.demos/app.2.cloud/template/petstore_domain_template.jar -domain=/u01/wins/wls1036/user_projects/domains/petstore_domain -user_name=weblogic -password=welcome1 -log=petstore_domain_creation.log

echo "********** STARTING NM (WEBLOGIC 10.3.6 - PETSTORE_DOMAIN) **************************"
cd /u01/wins/wls1036/wlserver_10.3/server/bin
nohup ./startNodeManager.sh &>nm.log </dev/null  &

sleep 5

nohup ./stopNodeManager.sh &>nmshut.log </dev/null  &

sleep 5

sed "s|StartScriptEnabled=false|StartScriptEnabled=true|g" -i /u01/wins/wls1036/wlserver_10.3/common/nodemanager/nodemanager.properties

nohup ./startNodeManager.sh &>nm.log </dev/null  &

echo "********** STARTING ADMIN (WEBLOGIC 10.3.6 - PETSTORE_DOMAIN) **************************"
cd /u01/wins/wls1036/user_projects/domains/petstore_domain/bin
nohup ./startWebLogic.sh &>adminserver.log </dev/null  &

sleep 2

tail -f /u01/wins/wls1036/user_projects/domains/petstore_domain/bin/adminserver.log | while read LOGLINE
do
   [[ "${LOGLINE}" == *"Server state changed to RUNNING"* ]] && pkill -P $$ tail
done

echo "********** ADMIN SERVER (WEBLOGIC 10.3.6 - DOMAIN1036) HAS BEEN STARTED ***********"

cd /u01/content/weblogic-innovation-seminars/cloud.demos/app.2.cloud
cp wlst/deployPetstore_template.py wlst/deployPetstore.py
sed "s|@database.dba.pass@|${dbpassword}|g; s|@database.pdb@|${pdb}|g" -i wlst/deployPetstore.py

echo "********** DEPLOY PETSTORE (WEBLOGIC 10.3.6 - PETSTORE_DOMAIN) ***********"
export DOMAIN_HOME=/u01/wins/wls1036/user_projects/domains/petstore_domain


cd $DOMAIN_HOME/bin 
. ./setDomainEnv.sh

$JAVA_HOME/bin/java -Dweblogic.deploy.UploadLargeFile=true weblogic.WLST /u01/content/weblogic-innovation-seminars/cloud.demos/app.2.cloud/wlst/deployPetstore.py



