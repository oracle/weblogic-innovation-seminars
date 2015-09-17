#!/bin/bash

DOMAIN_DIR=/u01/wins/wls1221/user_projects/domains/base_domain
STOP_DP_SCRIPT=/u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab3/stopMedrec-DevDP.py

. $DOMAIN_DIR/bin/setDomainEnv.sh
java weblogic.WLST -skipWLSModuleScanning $STOP_DP_SCRIPT 
