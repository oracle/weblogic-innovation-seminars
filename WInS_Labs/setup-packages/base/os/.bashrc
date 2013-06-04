# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# User specific aliases and functions
. /u01/app/oracle/product/11.2.0/xe/bin/oracle_env.sh
export JAVA_HOME=/labs/java/jdk1.6.0_29
export M2_HOME=/labs/third-party/apache-maven-3.0.4
export MW_HOME=/labs/Oracle/Middleware
export WL_HOME=$MW_HOME/wlserver_12.1
export LABS_HOME=/labs/content/WInS_labs
export ANT_HOME=$MW_HOME/modules/org.apache.ant_1.7.1
export APPGRID_DOMAIN_HOME=$MW_HOME/user_projects/domains/appgrid_domain

export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$ANT_HOME/bin:$WL_HOME/common/bin:$PATH

alias tnm="tail -f /labs/Oracle/Middleware/wlserver_12.1/common/nodemanager/nodemanager.log"
alias tnm2="tail -f /labs/Oracle/Middleware/wlserver_12.1/common/nodemanager2/nodemanager.log"

alias tail-ag-adm="tail -f $APPGRID_DOMAIN_HOME/servers/AdminServer/logs/AdminServer.out"
alias tail-ag-ms1="tail -f $APPGRID_DOMAIN_HOME/servers/appgrid-ms-1/logs/appgrid-ms-1.out"
alias tail-ag-ms2="tail -f $APPGRID_DOMAIN_HOME/servers/appgrid-ms-2/logs/appgrid-ms-2.out"

