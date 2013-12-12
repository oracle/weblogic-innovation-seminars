#!/bin/bash

# Change this to point to the JDK installation directory
export JAVA_HOME="/usr/java/latest"

# Other paths
export DEMO_JAR="$PWD/../application/demo.jar"
export DEMO_TEMP_DIR="$PWD/temp"

# Clean up and setup infrastructure
rm -Rf /S /Q "$DEMO_TEMP_DIR"
mkdir "$DEMO_TEMP_DIR"

# Starting demo
echo Starting Demo
cd $DEMO_TEMP_DIR
"$JAVA_HOME/bin/java" -Djava.io.tmpdir="$DEMO_TEMP_DIR" -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=7091 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=defaultrecording=true -jar "$DEMO_JAR"
