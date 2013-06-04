#!/bin/bash
#
# Copyright (c) 2012,2013, Oracle and/or its affiliates. All rights reserved. 
#
# This script will connect to the domainRuntime() mbeans on the admin server and 
# allow you to view the coherence MBeans for the entire coherence cluster.
#

cd /home/oracle/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/bin/
. ./setDomainEnv.sh

cp -Rf  /u01/content/weblogic-innovations-seminars/WInS_Demos/grid-archive-examples/src/main/wlst/gar_common.py /home/oracle/Oracle/Middleware/Oracle_Home/wlserver/common/wlst/