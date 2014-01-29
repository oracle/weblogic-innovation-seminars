#!/bin/bash
#
# Copyright (c) 2012,2013, Oracle and/or its affiliates. All rights reserved. 
#
# This script will run the DRCP application Client
#


/usr/java/jdk1.7.0_40/bin/java -Dfile.encoding=UTF-8 -classpath ./wins.drcp.client.jar:./javax.json-1.0.jar com.oracle.wins.drcp.DRCPLoaderClient 100 2 1 2 http://wins-vbox.localdomain:7101/drcpdemo/DRCPDemo http://wins-vbox.localdomain:7102/drcpdemo/DRCPDemo

