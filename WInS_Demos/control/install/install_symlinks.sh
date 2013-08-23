#!/bin/sh

set -x

CONTROL_DIR="/u01/content/weblogic-innovation-seminars/WInS_Demos/control"

sudo rm /etc/httpd/conf/httpd.conf
sudo ln -s ${CONTROL_DIR}/control/httpd/httpd.conf /etc/httpd/conf/httpd.conf
sudo service httpd restart

sudo mkdir /security
sudo chown oracle:oinstall /security

sudo ln -s ${CONTROL_DIR}/init.d/oracle-12c-cdb.sh /etc/init.d/oracle-12c-cdb
sudo ln -s ${CONTROL_DIR}/init.d/oracle-12c-cpb.sh /etc/init.d/oracle-12c-cpb
sudo ln -s ${CONTROL_DIR}/init.d/oracle-otd.sh /etc/init.d/oracle-otd

# list files look and link to desktop
for FILE in `ls ${CONTROL_DIR}/desktop/*.desktop`
do
 echo "Processing $FILE"
 sudo ln -s ${CONTROL_DIR}/control/desktop/$FILE ~oracle/Desktop/$FILE
done

sudo ln -s ${CONTROL_DIR}/control/maven/settings.xml /u01/third-party/apache-maven-3.0.5/conf/settings.xml

mkdir -p /u01/wls1212/wlserver/common/nodemanager

sudo cp ${CONTROL_DIR}/nodemanager/nodemanager.properties /u01/wls1212/wlserver/common/nodemanager/nodemanager.properties

sudo ln -s ${CONTROL_DIR}/control/xinetd.d/nodemanagersvc1_1212 /etc/xinetd.d/

sudo chown -R oracle:oinstall /u01