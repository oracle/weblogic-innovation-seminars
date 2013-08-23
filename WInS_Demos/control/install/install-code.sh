#!/bin/sh

sudo mkdir -p /u01/content
sudo chown oracle:oinstall /u01/content

cd /u01/content

git clone https://github.com/oracle-weblogic/weblogic-innovation-seminars.git
git clone https://github.com/jeffreyawest/oracle-parcel-service.git
