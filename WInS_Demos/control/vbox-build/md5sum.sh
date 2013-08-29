#!/bin/sh

JAVA_HOME=$JAVA_HOME
${JAVA_HOME}/bin/javac ChecksumTool.java
${JAVA_HOME}/bin/java ChecksumTool $*