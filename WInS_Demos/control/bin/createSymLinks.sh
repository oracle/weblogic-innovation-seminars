#!/bin/sh

cd /etc/httpd/conf/
sudo ln -s $DEMOS_HOME/control/http/httpd.conf

cd /etc/xinitd.d
sudo ln -s $DEMOS_HOME/control/xinetd.d/nodemgrsvc1_1212