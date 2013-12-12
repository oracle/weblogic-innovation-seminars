#!/bin/bash

# Change this to point to the JDK installation directory
export JAVA_HOME="/usr/java/latest"

# Other paths
export JMC_TEMP_DIR="$PWD/jmc_temp"

# Clean up and setup infrastructure
rm -Rf "$JMC_TEMP_DIR"
mkdir "$JMC_TEMP_DIR"

# Starting demo
echo Starting Mission Control
cd "$JMC_TEMP_DIR"
"$JAVA_HOME/bin/jmc" -data "$JMC_TEMP_DIR" -debug
