#!/bin/bash

echo "SUDO1==============================START"

echo "http_proxy=$http_proxy"
echo "https_proxy=$https_proxy"

rm -f /var/run/yum.pid

sleep 3

yum makecache fast

yum -y install zlib-devel-1.2.7-15.el7.x86_64

yum -y install openssl-devel-1.0.1e-51.el7_2.5.x86_64

echo "SUDO1================================END"
