#!/bin/sh

WLS_JAR="/home/oracle/Downloads/wls_generic.jar"
RESPONSES="/u01/content/weblogic-innovations-seminars/WInS_Demos/control/responses/wls1212_responses.rsp"


/usr/java/latest/bin/java -jar ${WLS_JAR} -silent -responseFile ${RESPONSES}
