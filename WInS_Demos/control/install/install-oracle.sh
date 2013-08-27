#!/bin/sh

echo "${0} | CONTROL_DIR=${CONTROL_DIR}"


if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable!"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi


grep winsEnv ~oracle/.bashrc
if [ "$?" != "0" ]; then
  echo ". ${CONTROL_DIR}/bin/winsEnv.sh" >> ~oracle/.bashrc
fi

sudo chown -R oracle:oinstall /u01
sudo chmod -R 775 /u01

#####################################################################################################################
check_network

SW_PACKAGE="SQL Developer"
echo "## ${SW_PACKAGE} ########################################################################################################################"
sudo rpm -Uvh ${SOFTWARE_SOURCE}/sqldeveloper-3.2.20.09.87-1.noarch.rpm


#####################################################################################################################
check_network

SW_PACKAGE="OEPE"
echo "## ${SW_PACKAGE} ########################################################################################################################"

mkdir -p /u01/oepe
exec_command "$SW_PACKAGE" "unzip ${SOFTWARE_SOURCE}/OEPE_12.1.2/oepe-12.1.2.1-kepler-distro-linux-gtk-x86_64.zip -d /u01/oepe"


#SW_PACKAGE="WLS 12.1.2"
#echo "## ${SW_PACKAGE} ########################################################################################################################"
#
#exec_command "$SW_PACKAGE" "${JAVA} -jar ${SOFTWARE_SOURCE}/WLS_12.1.2/wls_121200.jar -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/wls-install.rsp -invPtrLoc ${ORA_INVENTORY}"
#

#####################################################################################################################
check_network

SW_PACKAGE="JDeveloper 12c"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${JAVA} -jar ${SOFTWARE_SOURCE}/JDEVGENERIC_12.1.2_V38525-01/jdev_suite_121200.jar -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/jdev-install.rsp -invPtrLoc ${ORA_INVENTORY}"


#####################################################################################################################
check_network

SW_PACKAGE="OFM Infrastructure"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${JAVA} -jar ${SOFTWARE_SOURCE}/OFMINFRA_12.1.2_V38521/fmw_infra_121200.jar -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/ofm-install.rsp -invPtrLoc ${ORA_INVENTORY}"


#####################################################################################################################
check_network

SW_PACKAGE="WLS Maven Plugin"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "WLS Maven Plugin" "${M2_HOME}/bin/mvn install:install-file -Dfile=$WL_HOME/server/lib/wls-maven-plugin.jar -DpomFile=$WL_HOME/server/lib/pom.xml"


SW_PACKAGE="Maven Sync Plugin"
echo "## ${SW_PACKAGE} ########################################################################################################################"
/oracle_common/plugins/maven/com/oracle/maven/oracle-maven-sync/12.1.2/oracle-maven-sync.12.1.2.pom.
exec_command "WLS Maven Plugin" "${M2_HOME}/bin/mvn install:install-file -Dfile=$MW_HOME/oracle_common/plugins/maven/com/oracle/maven/oracle-maven-sync/12.1.2/oracle-maven-sync.12.1.2.jar -DpomFile=$MW_HOME/oracle_common/plugins/maven/com/oracle/maven/oracle-maven-sync/12.1.2/oracle-maven-sync.12.1.2.pom"


SW_PACKAGE="WLS Zip Distribution"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "WLS Zip Distribution" "${M2_HOME}/bin/mvn install:install-file -Dfile=/software/WLS_12.1.2/wls1212_dev.zip -DgroupId=com.oracle.weblogic -DartifactId=wls-dev -Dpackaging=zip -Dversion=12.1.2.0"


#####################################################################################################################
check_network

SW_PACKAGE="WLS NodeManager"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "mkdir -p /u01/wls1212/wlserver/common/nodemanager"
exec_command "$SW_PACKAGE" "sudo cp ${CONTROL_DIR}/nodemanager/nodemanager.properties /u01/wls1212/wlserver/common/nodemanager/nodemanager.properties"
exec_command "$SW_PACKAGE" "sudo chown oracle:oinstall /u01/wls1212/wlserver/common/nodemanager/nodemanager.properties"
exec_command "$SW_PACKAGE" "sudo rm -f /etc/xinetd.d/nodemanager*"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/xinetd.d/nodemanagersvc1_1212 /etc/xinetd.d/"
exec_command "$SW_PACKAGE" "sudo chkconfig xinetd on"


#####################################################################################################################
check_network

SW_PACKAGE="OUD"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${SOFTWARE_SOURCE}/OUD_11.1.2.1_V37478-01/Disk1/runInstaller -jreLoc ${JAVA_HOME} -ignoreSysPrereqs -silent -responseFile ${RESPONSES_SOURCE}/oud-install.rsp -invPtrLoc ${ORA_INVENTORY} -waitforcompletion" 


#####################################################################################################################
check_network

SW_PACKAGE="OTD"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${SOFTWARE_SOURCE}/OTD_11.1.1.7/Disk1/runInstaller -jreLoc ${JAVA_HOME} -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/otd-install.rsp -invPtrLoc ${ORA_INVENTORY}  -waitforcompletion" 
exec_command "$SW_PACKAGE" "sudo rm -f /etc/init.d/oracle-otd"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/init.d/oracle-otd.sh /etc/init.d/oracle-otd"
exec_command "$SW_PACKAGE" "sudo chkconfig oracle-otd on"


#####################################################################################################################
check_network

SW_PACKAGE="OHS"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${SOFTWARE_SOURCE}/OHS_12.1.2_V38524-01/ohs_121200_linux64.bin -silent -response ${RESPONSES_SOURCE}/ohs-install.rsp -invPtrLoc ${ORA_INVENTORY}" 


#####################################################################################################################
check_network

SW_PACKAGE="WLS Plugins"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "mkdir -p /u01/weblogic-plugins-12.1.2" 
exec_command "$SW_PACKAGE" "unzip ${SOFTWARE_SOURCE}/WLPLUGIN_12.1.2_V38594-01/WLSPlugin12c-64bit-Apache2.2-linux64-x86_64.zip -d /u01/weblogic-plugins-12.1.2"
exec_command "$SW_PACKAGE" "sudo rm -f /etc/httpd/conf/httpd.conf"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/httpd/httpd.conf /etc/httpd/conf/httpd.conf"
exec_command "$SW_PACKAGE" "sudo chmod 644 /etc/httpd/conf/httpd.conf"
exec_command "$SW_PACKAGE" "sudo service httpd restart"


#####################################################################################################################
check_network

SW_PACKAGE="TOPLINK"
echo "## ${SW_PACKAGE} ########################################################################################################################"
exec_command "$SW_PACKAGE" "${JAVA} -jar ${SOFTWARE_SOURCE}/TOPLINK_12.1.2/toplink_quick_121200.jar  ORACLE_HOME=/u01/wls1212 -invPtrLoc ${ORA_INVENTORY}"

echo `hostname`
read -p "Continue?" yn


#####################################################################################################################
check_network

SW_PACKAGE="Database 12c"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "sudo rm -f /etc/oratab"
exec_command "$SW_PACKAGE" "${SOFTWARE_SOURCE}/ODB_12.1.2/database/runInstaller -jreLoc ${JAVA_HOME} -ignorePrereq -silent -responseFile ${RESPONSES_SOURCE}/odb-install.rsp -invPtrLoc ${ORA_INVENTORY} -showProgress -waitforcompletion"

check_network

exec_command "$SW_PACKAGE" "sudo ${ORACLE_HOME}/root.sh"
exec_command "$SW_PACKAGE" "sudo hostname wins-vbox.localdomain"
check_network

exec_command "$SW_PACKAGE" "${ORACLE_HOME}/cfgtoollogs/configToolAllCommands RESPONSE_FILE=${RESPONSES_SOURCE}/cfgrsp.properties INV_PTR_LOC=${ORA_INVENTORY}"
check_network

exec_command "$SW_PACKAGE" "sudo rm -f /etc/init.d/oracle-12c-cdb"
exec_command "$SW_PACKAGE" "sudo rm -f /etc/init.d/oracle-12c-pdb"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/init.d/oracle-12c-cdb.sh /etc/init.d/oracle-12c-cdb"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/init.d/oracle-12c-pdb.sh /etc/init.d/oracle-12c-pdb"
exec_command "$SW_PACKAGE" "sudo cp ${CONTROL_DIR}/system/etc/oratab /etc/oratab"


check_network

exec_command "$SW_PACKAGE" "sudo chkconfig oracle-12c-cdb on"
exec_command "$SW_PACKAGE" "sudo chkconfig oracle-12c-pdb on"
exec_command "$SW_PACKAGE" "sudo service oracle-12c-cdb start"
exec_command "$SW_PACKAGE" "sudo service oracle-12c-pdb start"
#exec_command "$SW_PACKAGE" "sqlplus 'sys/welcome1 as sysdba' @${CONTROL_DIR}/install/sql/setup-pdb.sql"


#####################################################################################################################
check_network

SW_PACKAGE="Final Permissions"
echo "## ${SW_PACKAGE} ########################################################################################################################"
exec_command "$SW_PACKAGE" "sudo chown -R oracle:oinstall /u01" 