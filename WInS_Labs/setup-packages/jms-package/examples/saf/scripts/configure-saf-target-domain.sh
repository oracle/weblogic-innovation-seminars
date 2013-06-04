#!/bin/bash
echo Configuring SAF for the target domain
wlst.sh $LABS_HOME/setup-packages/jms-package/wlst/configure_saf_target_domain.py

