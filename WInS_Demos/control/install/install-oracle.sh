#!/bin/sh

set -x

SOFTWARE_SOURCE="/software/oracle"
ORA_INVENTORY="/etc/oraInst.loc"
JAVA_HOME="/usr/java/latest"
JAVA="${JAVA_HOME}/bin/java"
#CONTROL_DIR="/u01/content/weblogic-innovation-seminars/WInS_Demos/control"
CONTROL_DIR="/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos/control"
RESPONSES_SOURCE="${CONTROL_DIR}/install/responses"

grep winsEnv ~oracle/.bashrc
if [ "$?" != "0" ]; then
  echo ". /u01/content/weblogic-innovation-seminars/WInS_Demos/control/bin/winsEnv.sh" >> ~oracle/.bashrc
fi

sudo cp  ${RESPONSES_SOURCE}/../oraInst.loc /etc
sudo chown oracle:oinstall /etc/oraInst.loc

sudo mkdir /u01
sudo chown -R oracle:oinstall /u01
sudo chmod -R 775 /u01

sudo rpm -Uvh ${SOFTWARE_SOURCE}/sqldeveloper-3.2.20.09.87-1.noarch.rpm

# OEPE:
mkdir -p /u01/oepe
unzip ${SOFTWARE_SOURCE}/OEPE_12.1.2/oepe-12.1.2.1-kepler-distro-linux-gtk-x86_64.zip -d /u01/oepe

# JDEV: ########################################################################################################################

${JAVA} -jar ${SOFTWARE_SOURCE}/JDEVGENERIC_12.1.2_V38525-01/jdev_suite_121200.jar -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/jdev-install.rsp -invPtrLoc ${ORA_INVENTORY}

if [ "$?" != "0" ];
then
	echo "Error installing OFM Infrastructure!"
	exit 1
fi

read -p "Done.  Press enter to continue" yn


# FMW Infrastructure: ########################################################################################################################

${JAVA} -jar ${SOFTWARE_SOURCE}/OFMINFRA_12.1.2_V38521/fmw_infra_121200.jar -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/ofm-install.rsp -invPtrLoc ${ORA_INVENTORY}

if [ "$?" != "0" ];
then
	echo "Error installing OFM Infrastructure!"
	exit 1
fi

read -p "Done.  Press enter to continue" yn


# Database: ########################################################################################################################

${SOFTWARE_SOURCE}/ODB_12.1.2/database/runInstaller -jreLoc ${JAVA_HOME} -ignorePrereq -silent -responseFile ${RESPONSES_SOURCE}/odb-install.rsp -invPtrLoc ${ORA_INVENTORY} -showProgress -waitforcompletion

if [ "$?" != "0" ];
then
	echo "Error installing DATABASE!"
	exit 1
fi

sudo /u01/app/oracle/product/12.1.0/dbhome_1/root.sh
/u01/app/oracle/product/12.1.0/dbhome_1/cfgtoollogs/configToolAllCommands RESPONSE_FILE=${RESPONSES_SOURCE}/cfgrsp.properties

# OUD - Unified Directory: ########################################################################################################################

${SOFTWARE_SOURCE}/OUD_11.1.2.1_V37478-01/Disk1/runInstaller -jreLoc ${JAVA_HOME} -ignoreSysPrereqs -silent -responseFile ${RESPONSES_SOURCE}/oud-install.rsp -invPtrLoc ${ORA_INVENTORY} -waitforcompletion
if [ "$?" != "0" ];
then
	echo "Error installing OUD!"
	exit 1
fi

# OTD: ########################################################################################################################

${SOFTWARE_SOURCE}/OTD_11.1.1.7/Disk1/runInstaller -jreLoc ${JAVA_HOME} -silent -ignoreSysPrereqs -responseFile ${RESPONSES_SOURCE}/otd-install.rsp -invPtrLoc ${ORA_INVENTORY}  -waitforcompletion
if [ "$?" != "0" ];
then
	echo "Error installing OTD!"
	exit 1
fi

# OHS: ########################################################################################################################

${SOFTWARE_SOURCE}/OHS_12.1.2_V38524-01/ohs_121200_linux64.bin -silent -response ${RESPONSES_SOURCE}/ohs-install.rsp -invPtrLoc ${ORA_INVENTORY}

# WLS Plugins: ########################################################################################################################

mkdir -p /u01/weblogic-plugins-12.1.2
unzip ${SOFTWARE_SOURCE}/WLPLUGIN_12.1.2_V38594-01/WLSPlugin12c-64bit-Apache2.2-linux64-x86_64.zip -d /u01/weblogic-plugins-12.1.2

# TOPLINK: ########################################################################################################################
${JAVA} -jar ${SOFTWARE_SOURCE}/TOPLINK_12.1.2/toplink_quick_121200.jar  ORACLE_HOME=/u01/wls1212 -invPtrLoc ${ORA_INVENTORY}

read -p "Done.  Press enter to continue" yn
