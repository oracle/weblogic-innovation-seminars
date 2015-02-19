#!/bin/sh

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# User specific aliases and functions

export CONFIG_JVM_ARGS=-Djava.security.egd=file:/dev/./urandom
export JAVA_OPTIONS=$CONFIG_JVM_ARGS

export SW_BASE="/u01"
export USER_BASE="/u01"

##################################################################

export ORACLE_HOME=/u01/app/oracle/product/12.1.0/dbhome_1
export ORACLE_SID=orcl
export PATH=$ORACLE_HOME/bin:$PATH

##################################################################

export CONTENT_HOME="${USER_BASE}/content"
export DEMOS_HOME="${CONTENT_HOME}/weblogic-innovation-seminars/WInS_Demos"
export MW_HOME="/u01/middleware"
export NM_HOME="/u01/middleware/user_projects/domains/weblogic-examples-domain/nodemanager"
export USER_PROJECTS="${MW_HOME}/user_projects"
export DOMAINS="${USER_PROJECTS}/domains"
export OPS_DOMAIN_HOME="${USER_PROJECTS}/domains/ops-cluster"

export JAVA_HOME="/usr/java/latest"
export PATH="${PATH}:${JAVA_HOME}/bin"

export PATH="${PATH}:${DEMOS_HOME}/control/bin"

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
###################################################################

export BASICOUT="${DOMAINS}/weblogic-basic-domain/servers/AdminServer/logs/AdminServer.out"

export ADMINOUT="${DOMAINS}/weblogic-examples-domain/servers/AdminServer/logs/AdminServer.out"
export ADMINLOG="${DOMAINS}/weblogic-examples-domain/servers/AdminServer/logs/AdminServer.log"

export MS1OUT="${DOMAINS}/weblogic-examples-domain/servers/ms-1/logs/ms-1.out"
export MS2OUT="${DOMAINS}/weblogic-examples-domain/servers/ms-2/logs/ms-2.out"

export MS1LOG="${DOMAINS}/weblogic-examples-domain/servers/ms-1/logs/ms-1.log"
export MS2LOG="${DOMAINS}/weblogic-examples-domain/servers/ms-2/logs/ms-2.log"

export TAIL_OPTS="--lines=500 --follow=name --retry"
###################################################################

alias demo="mvn -P demo -DskipTests=false"

alias tnm="tail ${TAIL_OPTS} ${NM_HOME}/nodemanager.log"
alias tnm2="tail ${TAIL_OPTS} ${NM_HOME}2/nodemanager.log"

alias tbasic="tail ${TAIL_OPTS} ${BASICOUT}"

alias tadmin="tail ${TAIL_OPTS} ${ADMINOUT}"
alias ladmin="less ${ADMINOUT}"

alias tadminlog="tail ${TAIL_OPTS} ${ADMINLOG}"
alias ladminlog="less ${ADMINLOG}"

alias tms1="tail ${TAIL_OPTS} ${MS1OUT}"
alias lms1="less ${MS1OUT}"

alias tms1log="tail ${TAIL_OPTS} ${MS1LOG}"
alias lms1log="less ${MS1LOG}"

alias tms2="tail ${TAIL_OPTS} ${MS2OUT}"
alias lms2="less ${MS2OUT}"

alias tms2log="tail ${TAIL_OPTS} ${MS2LOG}"
alias lms2log="less ${MS2LOG}"

alias tsaf1="tail ${TAIL_OPTS} ${DOMAINS}/saf-target-domain/servers/ms-1/logs/ms-1.out"
alias tsaf2="tail ${TAIL_OPTS} ${DOMAINS}/saf-target-domain/servers/ms-2/logs/ms-2.out"
alias tsafadmin="tail ${TAIL_OPTS} ${DOMAINS}/saf-target-domain/servers/AdminServer/logs/AdminServer.out"

alias tcoh1="tail ${TAIL_OPTS} ${DOMAINS}/weblogic-examples-domain/servers_coherence/coh-1/logs/coh-1.out"
alias tcoh2="tail ${TAIL_OPTS} ${DOMAINS}/weblogic-examples-domain/servers_coherence/coh-2/logs/coh-2.out"

alias topsadmin="tail ${TAIL_OPTS} ${OPS_DOMAIN_HOME}/servers/AdminServer/logs/AdminServer.out"
alias tops1="tail ${TAIL_OPTS} ${OPS_DOMAIN_HOME}/servers/ms-1/logs/ms-1.out"
alias tops2="tail ${TAIL_OPTS} ${OPS_DOMAIN_HOME}/servers/ms-2/logs/ms-2.out"

export CGGA_DEMO_HOME=$DEMOS_HOME/../Coherence_Demos
export GG_HOME=$SW_BASE/goldengate
export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$GG_HOME:$PATH

export LD_LIBRARY_PATH=${JAVA_HOME}/jre/lib/amd64/server:$ORACLE_HOME/lib:$GG_HOME:$LD_LIBRARY_PATH

###################################################################

# Source global definitions
if [ -f /home/oracle/setProxy.sh ]; then
	. /home/oracle/setProxy.sh
fi

cat /home/oracle/Desktop/readme.txt

