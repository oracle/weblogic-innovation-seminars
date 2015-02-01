#!/bin/sh

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# User specific aliases and functions

export SW_BASE="/u01"
export USER_BASE="/u01"

##################################################################

export ORACLE_HOME=/u01/app/oracle/product/12.1.0/dbhome_1
export ORACLE_SID=orcl
export PATH=$ORACLE_HOME/bin:$PATH

##################################################################

export CONTENT_HOME="${USER_BASE}/content"
export DEMOS_HOME="${CONTENT_HOME}/weblogic-innovation-seminars/WInS_Demos"

export WORKSHOP_HOME="${CONTENT_HOME}/weblogic-workshop-1035"
export USER_PROJECTS="${MW_HOME}/user_projects"
export DOMAINS="${USER_PROJECTS}/domains"
export OPS_DOMAIN_HOME="${USER_PROJECTS}/domains/ops-cluster"

export JAVA_HOME=/usr/java/latest
export PATH=$JAVA_HOME/bin:$PATH

export M2_HOME="/u01/middleware/oracle_common/modules/org.apache.maven_3.0.5"
export MAVEN_HOME="${M2_HOME}"
export MAVEN_OPTS="-Xmx2048m -Xms512m -Dweblogic.security.SSL.ignoreHostnameVerification=true -Dweblogic.security.TrustKeyStore=DemoTrust -Dweblogic.nodemanager.sslHostNameVerificationEnabled=false"
export PATH="${PATH}:${M2_HOME}/bin"

export MW_HOME="${SW_BASE}/middleware"
export WL_HOME="${MW_HOME}/wlserver"
export PATH="${PATH}:${WL_HOME}/common/bin"

export PATH="${PATH}:${DEMOS_HOME}/control/bin"

###################################################################

# Source global definitions
if [ -f /home/oracle/setProxy.sh ]; then
	. /home/oracle/setProxy.sh
fi

cat /home/oracle/Desktop/readme.txt

