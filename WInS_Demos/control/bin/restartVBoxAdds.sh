#!/bin/bash

pid_list=`ps aux|grep "VBoxClient"|grep -v "grep"|awk '{print $2}'`

for pid in $pid_list
do
  echo "Killing VBoxClient pid=${pid}"
  kill -9 $pid
done

VBoxClient-all