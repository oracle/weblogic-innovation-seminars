#!/bin/bash

pid_list=`ps aux|grep nodem|grep -v "weblogic.Name"|grep -v "grep"|grep -v "tail" | grep -vi "weblogic.Name" |awk '{print $2}'`

for pid in $pid_list
do
  echo "Killing NodeManager pid=${pid}"
  kill -9 $pid
done