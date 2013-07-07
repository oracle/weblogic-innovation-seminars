#!/bin/bash

killNodeManager.sh

pid_list=`ps aux|grep "weblogic.Name"|grep -v "grep"|awk '{print $2}'`

for pid in $pid_list
do
  echo "Killing WebLogic pid=${pid}"
  kill -9 $pid
done