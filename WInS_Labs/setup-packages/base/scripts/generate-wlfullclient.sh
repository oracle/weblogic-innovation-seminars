#!/bin/bash
PWD_BACKUP=$PWD
if [ -f $WL_HOME/server/lib/wlfullclient.jar ];
then
  echo 'ok - wlfullclient.jar exists'
else  
  cd $WL_HOME/server/lib
  java -jar ./wljarbuilder.jar -profile wlfullclient
fi
cd $PWD_BACKUP

