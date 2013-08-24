#!/bin/sh

SOFTWARE_SOURCE="/software/oracle"
ORA_INVENTORY="/etc/oraInst.loc"
JAVA_HOME="/usr/java/latest"
JAVA="${JAVA_HOME}/bin/java"
#CONTROL_DIR="/u01/content/weblogic-innovation-seminars/WInS_Demos/control"
CONTROL_DIR="/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control"
RESPONSES_SOURCE="${CONTROL_DIR}/install/responses"
ORACLE_HOME="/u01/app/oracle/product/12.1/database"

exec_command()
{
	#Show the command
	echo "Executing command: [$2]"

		# Execute the command
		echo "Started at `date`"
		start_date=`date +%s`

		${2}

		if [ $? -ne 0 ]; then
			end_date=`date +%s`
			duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)
      echo "--Command complete (error) in ${duration} minutes"
      echo "Error installing SW Package: $1"
      exit 1
		else
			end_date=`date +%s`
			duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)
			echo "++Command complete (success) in ${duration} minutes"
		fi
}

grep winsEnv ~oracle/.bashrc
if [ "$?" != "0" ]; then
  echo ". ${CONTROL_DIR}/bin/winsEnv.sh" >> ~oracle/.bashrc
fi

sudo chown -R oracle:oinstall /u01
sudo chmod -R 775 /u01

SW_PACKAGE="SQL Developer"
echo "## ${SW_PACKAGE} ########################################################################################################################"
sudo rpm -Uvh ${SOFTWARE_SOURCE}/sqldeveloper-3.2.20.09.87-1.noarch.rpm


SW_PACKAGE="OEPE"
echo "## ${SW_PACKAGE} ########################################################################################################################"

mkdir -p /u01/oepe
exec_command "$SW_PACKAGE" "unzip ${SOFTWARE_SOURCE}/OEPE_12.1.2/oepe-12.1.2.1-kepler-distro-linux-gtk-x86_64.zip -d /u01/oepe"


SW_PACKAGE="JDeveloper 12c"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${JAVA} -jar ${SOFTWARE_SOURCE}/JDEVGENERIC_12.1.2_V38525-01/jdev_suite_121200.jar -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/jdev-install.rsp -invPtrLoc ${ORA_INVENTORY}" 


SW_PACKAGE="OFM Infrastructure"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${JAVA} -jar ${SOFTWARE_SOURCE}/OFMINFRA_12.1.2_V38521/fmw_infra_121200.jar -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/ofm-install.rsp -invPtrLoc ${ORA_INVENTORY}"
exec_command "$SW_PACKAGE" "mkdir -p /u01/wls1212/wlserver/common/nodemanager" 
exec_command "$SW_PACKAGE" "sudo cp ${CONTROL_DIR}/nodemanager/nodemanager.properties /u01/wls1212/wlserver/common/nodemanager/nodemanager.properties" 
exec_command "$SW_PACKAGE" "sudo rm -f /etc/xinetd.d/nodemanager*"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/xinetd.d/nodemanagersvc1_1212 /etc/xinetd.d/"
exec_command "$SW_PACKAGE" "sudo chkconfig xinetd on"
exec_command "$SW_PACKAGE" "sudo service xinetd restart"

SW_PACKAGE="OUD"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${SOFTWARE_SOURCE}/OUD_11.1.2.1_V37478-01/Disk1/runInstaller -jreLoc ${JAVA_HOME} -ignoreSysPrereqs -silent -responseFile ${RESPONSES_SOURCE}/oud-install.rsp -invPtrLoc ${ORA_INVENTORY} -waitforcompletion" 


SW_PACKAGE="OTD"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${SOFTWARE_SOURCE}/OTD_11.1.1.7/Disk1/runInstaller -jreLoc ${JAVA_HOME} -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/otd-install.rsp -invPtrLoc ${ORA_INVENTORY}  -waitforcompletion" 
exec_command "$SW_PACKAGE" "sudo rm -f /etc/init.d/oracle-otd"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/init.d/oracle-otd.sh /etc/init.d/oracle-otd"
exec_command "$SW_PACKAGE" "sudo chkconfig oracle-otd on"


SW_PACKAGE="OHS"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "${SOFTWARE_SOURCE}/OHS_12.1.2_V38524-01/ohs_121200_linux64.bin -silent -response ${RESPONSES_SOURCE}/ohs-install.rsp -invPtrLoc ${ORA_INVENTORY}" 


SW_PACKAGE="WLS Plugins"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "mkdir -p /u01/weblogic-plugins-12.1.2" 
exec_command "$SW_PACKAGE" "unzip ${SOFTWARE_SOURCE}/WLPLUGIN_12.1.2_V38594-01/WLSPlugin12c-64bit-Apache2.2-linux64-x86_64.zip -d /u01/weblogic-plugins-12.1.2"
exec_command "$SW_PACKAGE" "sudo rm -f /etc/httpd/conf/httpd.conf"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/httpd/httpd.conf /etc/httpd/conf/httpd.conf"
exec_command "$SW_PACKAGE" "sudo chmod 644 /etc/httpd/conf/httpd.conf"
exec_command "$SW_PACKAGE" "sudo service httpd restart"

SW_PACKAGE="TOPLINK"
echo "## ${SW_PACKAGE} ########################################################################################################################"
exec_command "$SW_PACKAGE" "${JAVA} -jar ${SOFTWARE_SOURCE}/TOPLINK_12.1.2/toplink_quick_121200.jar  ORACLE_HOME=/u01/wls1212 -invPtrLoc ${ORA_INVENTORY}"


SW_PACKAGE="Database 12c"
echo "## ${SW_PACKAGE} ########################################################################################################################"

exec_command "$SW_PACKAGE" "sudo rm -f /etc/oratab"
exec_command "$SW_PACKAGE" "${SOFTWARE_SOURCE}/ODB_12.1.2/database/runInstaller -jreLoc ${JAVA_HOME} -ignorePrereq -silent -responseFile ${RESPONSES_SOURCE}/odb-install.rsp -invPtrLoc ${ORA_INVENTORY} -showProgress -waitforcompletion"
exec_command "$SW_PACKAGE" "sudo ${ORACLE_HOME}/root.sh"
exec_command "$SW_PACKAGE" "${ORACLE_HOME}/cfgtoollogs/configToolAllCommands RESPONSE_FILE=${RESPONSES_SOURCE}/cfgrsp.properties INV_PTR_LOC=${ORA_INVENTORY}"
exec_command "$SW_PACKAGE" "sudo rm -f /etc/init.d/oracle-12c-cdb"
exec_command "$SW_PACKAGE" "sudo rm -f /etc/init.d/oracle-12c-pdb"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/init.d/oracle-12c-cdb.sh /etc/init.d/oracle-12c-cdb"
exec_command "$SW_PACKAGE" "sudo ln -s ${CONTROL_DIR}/system/etc/init.d/oracle-12c-pdb.sh /etc/init.d/oracle-12c-pdb"
exec_command "$SW_PACKAGE" "sudo cp ${CONTROL_DIR}/system/etc/oratab /etc/oratab"
exec_command "$SW_PACKAGE" "sudo chkconfig oracle-12c-cdb on"
exec_command "$SW_PACKAGE" "sudo chkconfig oracle-12c-pdb on"
exec_command "$SW_PACKAGE" "sudo service oracle-12c-cdb start"
exec_command "$SW_PACKAGE" "sudo service oracle-12c-pdb start"

SW_PACKAGE="Final Permissions"
echo "## ${SW_PACKAGE} ########################################################################################################################"
exec_command "$SW_PACKAGE" "sudo chown -R oracle:oinstall /u01" 