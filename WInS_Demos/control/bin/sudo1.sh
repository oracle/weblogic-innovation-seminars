#!/bin/bash

echo "SUDO1========================================START"

GIT_SYSTEM_PROXY_CHECK=`git config --get --system http.proxy`
if [ -n "$GIT_SYSTEM_PROXY_CHECK" ]; then
  export http_proxy=$GIT_SYSTEM_PROXY_CHECK
  export https_proxy=$GIT_SYSTEM_PROXY_CHECK
  echo "http_proxy=$http_proxy"
  echo "https_proxy=$https_proxy"
else
  unset http_proxy
  unset https_proxy
  echo "http_proxy=$http_proxy"
  echo "https_proxy=$https_proxy"
fi

rm -f /var/run/yum.pid

yum -y install zlib-devel-1.2.7-15.el7.x86_64

yum -y install openssl-devel-1.0.1e-51.el7_2.5.x86_64

echo "SUDO1========================================END"
