#!/bin/sh

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# User specific aliases and functions

export CONFIG_JVM_ARGS=-Djava.security.egd=file:/dev/./urandom

export SW_BASE="/u01"
export USER_BASE="/u01"

##################################################################

export ORACLE_HOME=/u01/app/oracle/product/12.1.0/dbhome_1
export ORACLE_SID=orcl
export PATH=$ORACLE_HOME/bin:$PATH

##################################################################

export CONTENT_HOME="${USER_BASE}/content"
export DEMOS_HOME="${CONTENT_HOME}/weblogic-innovation-seminars/WInS_Demos"

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

export ANT_HOME="${MW_HOME}/oracle_common/modules/org.apache.ant_1.9.2"
export PATH=$ANT_HOME/bin:$PATH

export CGGA_DEMO_HOME=$DEMOS_HOME/../Coherence_Demos
export GG_HOME=$SW_BASE/goldengate
export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$GG_HOME:$PATH

#export LD_LIBRARY_PATH=${ORACLE_HOME}/lib:/lib:/usr/lib
#export LD_LIBRARY_PATH="${LD_LIBRARY_PATH}:${ORACLE_HOME}/lib:${WL_HOME}/server/native/linux/i686:${PLUGIN_HOME}/lib"

export LD_LIBRARY_PATH=${JAVA_HOME}/jre/lib/amd64/server:$ORACLE_HOME/lib:$GG_HOME:$LD_LIBRARY_PATH

###################################################################

# Source global definitions
if [ -f /home/oracle/setProxy.sh ]; then
	. /home/oracle/setProxy.sh
fi

cat /home/oracle/Desktop/readme.txt

