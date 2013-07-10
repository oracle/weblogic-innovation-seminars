#!/bin/bash
#rm -Rf ~/.mozilla

zerofree()
{
  FILE=$1/zerofree_deleteme
  echo Creating temporary file: $FILE
  dd if=/dev/zero of=$FILE
  rm -Rf $FILE
  echo $FILE created and deleted...
}

SUCCESS="TRUE"


sqlplus 'sys/welcome1 as sysdba' @/u01/content/weblogic-innovation-seminars/WInS_Demos/environment/sql/truncate.sql

killWebLogic.sh

sudo rm -Rf $DOMAINS
sudo rm -Rf /tmp/*
sudo rm -Rf /var/cache/yum/*
sudo rm -Rf /u01/content/oracle-parcel-service/ops-weblogic/Oracle
sudo rm -Rf /home/oracle/Downloads/*
sudo rm -Rf /home/oracle/.m2/repository/com/oracle/weblogic/demo
sudo rm -Rf /home/oracle/.m2/repository/com/oracle/demo

cd /u01/content/weblogic-innovation-seminars/WInS_Demos
mvn clean

cd /u01/content/oracle-parcel-service
mvn clean

find /u01/content/weblogic-innovation-seminars -name "*.sh" -exec chmod +rx {} \;

zerofree /tmp
zerofree /u01

if [ -h /u01/content/weblogic-innovation-seminars ]; then
  rm /u01/content/weblogic-innovation-seminars
  cd /u01/content
  git clone http://github.com/oracle-weblogic/weblogic-innovation-seminars.git

  if [ "$?" != "0" ]; then
    SUCCESS="FALSE"
  fi
fi


if [ -h /u01/content/oracle-parcel-service ]; then
  rm /u01/content/oracle-parcel-service
  cd /u01/content
  git clone http://github.com/jeffreyawest/oracle-parcel-service.git

  if [ "$?" != "0" ]; then
    SUCCESS="FALSE"
  fi
fi

/u01/content/weblogic-innovation-seminars/WInS_Demos/control/updateDemos.sh
if [ "$?" != "0" ]; then
  SUCCESS="FALSE"
fi

/u01/content/weblogic-innovation-seminars/WInS_Demos/control/updateOPS.sh
if [ "$?" != "0" ]; then
  SUCCESS="FALSE"
fi

if [ "${SUCCESS}" == "TRUE" ]; then
  halt
else
  echo "Errors were encountered preparing for export!"
fi