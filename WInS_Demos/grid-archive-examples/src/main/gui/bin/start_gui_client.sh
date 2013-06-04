#!/bin/bash
#
# Copyright (c) 2012,2013, Oracle and/or its affiliates. All rights reserved. 
# Start the GUI Client
#
# setDomainEnv.sh must be run before running this

cd /home/oracle/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/bin/
. ./setDomainEnv.sh

cd /u01/content/weblogic-innovations-seminars/WInS_Demos/grid-archive-examples/src/main/gui/bin
export GUI_HOME=`pwd`/..
#

export CP=$GUI_HOME/lib/ExampleGUI.jar:$GUI_HOME/config:$GUI_HOME/lib/ExampleGAR.gar:$CLASSPATH

$JAVA_HOME/bin/java -cp $CP -Xmx128m -Xms128m -Dtangosol.coherence.tcmp.enabled=false -Dtangosol.coherence.cacheconfig=extend-cache-config.xml -Dtangosol.pof.enabled=true -Dtangosol.pof.config=META-INF/pof-config.xml weblogic.coherence.application.gui.ContactListenerGUI
