#!/bin/sh

grep /etc/fstab u01

if [ "$?" != "0" ];
then
  sudo echo "/dev/sdb1     /u01  ext4  defatuls  0 0" >> /etc/fstab
fi