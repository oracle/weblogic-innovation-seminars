#!/bin/bash
ps auxf |grep 'appgrid-ms-1'|`awk '{ print "kill -KILL " $2 }'`
