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


sqlplus 'sys/welcome1 as sysdba' @/u01/content/weblogic-innovation-seminars/WInS_Demos/environment/sql/truncate.sql

killWebLogic.sh

sudo rm -Rf $DOMAINS
sudo rm -Rf /tmp/*
sudo rm -Rf /var/cache/yum/*
sudo rm -Rf /u01/content/oracle-parcel-service/ops-weblogic/ops-embedded/Oracle
sudo rm -Rf /home/oracle/Downloads/*
sudo rm -Rf /home/oracle/.m2/repository/com/oracle/weblogic/demo
sudo rm -Rf /home/oracle/.m2/repository/com/oracle/demo

# Check for symlinks on the content directories.  If they are links, delete them
# they should be put back by the update*.sh scripts.
if [ -h /u01/content/weblogic-innovation-seminars ]; then
  rm /u01/content/weblogic-innovation-seminars
fi

if [ -h /u01/content/oracle-parcel-service ]; then
  rm /u01/content/oracle-parcel-service
fi

/u01/content/weblogic-innovation-seminars/WInS_Demos/control/updateDemos.sh
/u01/content/weblogic-innovation-seminars/WInS_Demos/control/updateOPS.sh

cd /u01/content/weblogic-innovation-seminars/WInS_Demos
mvn clean

cd /u01/content/oracle-parcel-service
mvn clean

find /u01/content/weblogic-innovation-seminars -name "*.sh" -exec chmod +rx {} \;

zerofree /tmp
zerofree /u01


halt