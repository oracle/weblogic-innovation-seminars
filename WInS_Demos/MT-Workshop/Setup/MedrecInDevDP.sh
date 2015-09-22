#!/bin/bash

DOMAIN_DIR=/u01/wins/wls1221/user_projects/domains/dev_domain
CONFIGURE_RG_SCRIPT=/u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Setup/MedrecInDevDP.py

. $DOMAIN_DIR/bin/setDomainEnv.sh
java weblogic.WLST -skipWLSModuleScanning $CONFIGURE_RG_SCRIPT 
