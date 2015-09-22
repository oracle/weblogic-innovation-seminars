#!/bin/bash

DOMAIN_DIR=/u01/wins/wls1221/user_projects/domains/base_domain
NON_MT_SCRIPT=/u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Setup/NonMTSetup.py

. $DOMAIN_DIR/bin/setDomainEnv.sh
java weblogic.WLST -skipWLSModuleScanning $NON_MT_SCRIPT 
