#!/bin/bash
$LABS_HOME/setup-packages/base/scripts/generate-wlfullclient.sh
java -classpath $WL_HOME/server/lib/wlfullclient.jar:$LABS_HOME/setup-packages/jms-package/lib/jms-sender.jar com.oracle.appgrid.example.jms.migration.MigrationProducer 500 500 1
