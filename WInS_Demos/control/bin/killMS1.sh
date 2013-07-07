#!/bin/bash

pid_list=`ps aux|grep "weblogic.Name=ms-1"|grep 7001|grep "weblogic.Server" | grep -v "grep"|awk '{print $2}'`

for pid in $pid_list
do
  echo "Killing WebLogic Managed Server [ms-1] pid=${pid}"
  kill -9 $pid
done