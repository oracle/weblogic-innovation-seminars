#!/bin/bash
#
# Copyright (c) 2012,2013, Oracle and/or its affiliates. All rights reserved. 
#
# This script will connect to the domainRuntime() mbeans on the admin server and 
# allow you to view the coherence MBeans for the entire coherence cluster.
#

cd /u01/wls1212/user_projects/domains/base_domain/bin/
. ./setDomainEnv.sh

if [ -z "$WL_HOME" ] ; then
   echo "You must run setDomainEnv.sh"
   exit 1
fi

$JAVA_HOME/bin/jconsole -J-Djava.class.path=$JAVA_HOME/lib/jconsole.jar:$JAVA_HOME/lib/tools.jar:$WL_HOME/server/lib/wljmxclient.jar:$WL_HOME/server/lib/weblogic.jar -J-Djmx.remote.protocol.provider.pkgs=weblogic.management.remote -debug  
#
# copy the following service url and enter user and password for remote connection in jconsole
#
# service:jmx:iiop://127.0.0.1:7001/jndi/weblogic.management.mbeanservers.domainruntime
