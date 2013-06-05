#!/bin/sh

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi
export SVN_EDITOR="vi"

# User specific aliases and functions

export SW_BASE="/u01"
export USER_BASE="/u01"

##################################################################

export ORACLE_HOME="${SW_BASE}/app/oracle/product/11.2.0/xe"
export ORACLE_SID="XE"
export NLS_LANG=`$ORACLE_HOME/bin/nls_lang.sh`
export PATH="${ORACLE_HOME}/bin:${PATH}"

##################################################################

export THIRD_PARTY="/u01/third-party"

export ANT_HOME="${THIRD_PARTY}/apache-ant-1.8.4"
export M2_HOME="${THIRD_PARTY}/apache-maven-3.0.4"
export MAVEN_HOME="${M2_HOME}"
export MAVEN_OPTS="-Dweblogic.security.SSL.ignoreHostnameVerification=true -Dweblogic.security.TrustKeyStore=DemoTrust -Dweblogic.nodemanager.sslHostNameVerificationEnabled=false"

export MW_HOME="${SW_BASE}/wls1212"
export WL_HOME="${MW_HOME}/wlserver"
export NM_HOME="${WL_HOME}/common/nodemanager"
export PLUGIN_HOME="/u01/wls-plugins-1.1"
export LD_LIBRARY_PATH="${LD_LIBRARY_PATH}:${ORACLE_HOME}/lib:${WL_HOME}/server/native/linux/i686:${PLUGIN_HOME}/lib"
export COHERENCE_HOME="${MW_HOME}/coherence"

export CONTENT_HOME="${USER_BASE}/content"
export DEMOS_HOME="${CONTENT_HOME}/weblogic-innovation-seminars/WInS_Demos"

export WORKSHOP_HOME="${CONTENT_HOME}/weblogic-workshop-1035"
export USER_PROJECTS="${MW_HOME}/user_projects"
export DOMAINS="${USER_PROJECTS}/domains"
export OPS_DOMAIN_HOME="${USER_PROJECTS}/domains/ops_domain"

export JAVA_HOME="/u01/java/jrockit-jdk1.6.0_45-R28.2.7-4.1.0"

export PATH="${JAVA_HOME}/bin:${PATH}:${ANT_HOME}/bin:${M2_HOME}/bin:${WL_HOME}/common/bin:${DEMOS_HOME}/control/bin"

###################################################################

export BASICOUT="${DOMAINS}/weblogic-basic-domain/servers/AdminServer/logs/AdminServer.out"

export ADMINOUT="${DOMAINS}/weblogic-examples-domain/servers/AdminServer/logs/AdminServer.out"
export ADMINLOG="${DOMAINS}/weblogic-examples-domain/servers/AdminServer/logs/AdminServer.log"

export MS1OUT="${DOMAINS}/weblogic-examples-domain/servers/ms-1/logs/ms-1.out"
export MS2OUT="${DOMAINS}/weblogic-examples-domain/servers/ms-2/logs/ms-2.out"

export MS1LOG="${DOMAINS}/weblogic-examples-domain/servers/ms-1/logs/ms-1.log"
export MS2LOG="${DOMAINS}/weblogic-examples-domain/servers/ms-2/logs/ms-2.log"

###################################################################

alias demo="mvn -P demo -DskipTests=false"

alias tnm="tail --lines=500 --follow=name --retry ${NM_HOME}/nodemanager.log"
alias tnm2="tail --lines=500 --follow=name --retry ${NM_HOME}2/nodemanager.log"

alias tbasic="tail --lines=500 --follow=name --retry ${BASICOUT}"

alias tadmin="tail --lines=500 --follow=name --retry ${ADMINOUT}"
alias tadminlog="tail --lines=500 --follow=name --retry ${ADMINLOG}"

alias tms1="tail --lines=500 --follow=name --retry ${MS1OUT}"
alias tms1log="tail --lines=500 --follow=name --retry ${MS1LOG}"

alias tms2="tail --lines=500 --follow=name --retry ${MS2OUT}"
alias tms2log="tail --lines=500 --follow=name --retry ${MS2LOG}"

alias tsaf1="tail --lines=500 --follow=name --retry ${DOMAINS}/saf_target_domain/servers/ms-1/logs/ms-1.out"
alias tsaf2="tail --lines=500 --follow=name --retry ${DOMAINS}/saf_target_domain/servers/ms-2/logs/ms-2.out"
alias tsafadmin="tail --lines=500 --follow=name --retry ${DOMAINS}/saf_target_domain/servers/AdminServer/logs/AdminServer.out"

alias tcoh1="tail --lines=500 --follow=name --retry ${DOMAINS}/weblogic-examples-domain/servers_coherence/coh-1/logs/coh-1.out"
alias tcoh2="tail --lines=500 --follow=name --retry ${DOMAINS}/weblogic-examples-domain/servers_coherence/coh-2/logs/coh-2.out"

alias tops1="tail --lines=500 --follow=name --retry ${OPS_DOMAIN_HOME}/servers/ms-1/logs/ms-1.out"
alias tops2="tail --lines=500 --follow=name --retry ${OPS_DOMAIN_HOME}/servers/ms-2/logs/ms-2.out"

alias tcohweb1="tail --lines=500 --follow=name --retry ${OPS_DOMAIN_HOME}/servers_coherence/coh-1/logs/coh-1.out"
alias tcohweb2="tail --lines=500 --follow=name --retry ${OPS_DOMAIN_HOME}/servers_coherence/coh-2/logs/coh-2.out"

alias tcohtlg1="tail --lines=500 --follow=name --retry ${OPS_DOMAIN_HOME}/servers_coherence/ops-coh-tlg-1/logs/ops-coh-tlg-1.out"
alias tcohtlg2="tail --lines=500 --follow=name --retry ${OPS_DOMAIN_HOME}/servers_coherence/ops-coh-tlg-2/logs/ops-coh-tlg-2.out"

alias ttns="tail  --lines=500 --follow=name --retry ${SW_BASE}/app/oracle/diag/tnslsnr/wins-vbox/listener/trace/listener.log"

sudo route add -net 224.0.0.0 netmask 240.0.0.0 dev eth4 > /dev/null 2>&1

. /home/oracle/setProxy.sh

echo "Please note:"
echo "1) Ensure you have the latest WInS Demos code by running the \"Update WInS Demos\" command on the desktop!"
echo "2) Ensure you have configured the proxy for the Oracle network if you are on the Oracle Network or VPN!"
echo ""
