#!/bin/sh

mount -n -o ro,remount /dev/sdb1 /u01
/sbin/zerofree /dev/sdb1

mount -n -o ro,remount /dev/sdc1 /software
/sbin/zerofree /dev/sdc1

mount -n -o ro,remount /dev/VolGroup-lv_root /
/sbin/zerofree /dev/VolGroup-lv_root