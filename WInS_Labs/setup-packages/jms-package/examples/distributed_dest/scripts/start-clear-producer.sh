#!/bin/bash
$LABS_HOME/setup-packages/base/scripts/generate-wlfullclient.sh
java -classpath $WL_HOME/server/lib/wlfullclient.jar:$LABS_HOME/setup-packages/jms-package/lib/jms-sender-12.1.1.0.jar com.oracle.example.jms.distributed.topic.ClearScreen
