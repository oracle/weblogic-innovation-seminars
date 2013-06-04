#!/bin/bash
export DOMAIN_NAME=$1
echo Setting up domain $DOMAIN_NAME
wlst.sh $PWD/domains/$DOMAIN_NAME/wlst/configure.py
#$PWD/deploy.sh -Dapplication.list=$DOMAIN_NAME-listener -Denvironment.list=EXAMPLE -Dconfirm.settings=y
cp -vrf $PWD/domains/$DOMAIN_NAME/overlay/bin/* $MW_HOME/user_projects/domains/$DOMAIN_NAME/bin
cp -vrf $PWD/domains/$DOMAIN_NAME/overlay/servers/* $MW_HOME/user_projects/domains/$DOMAIN_NAME/servers
