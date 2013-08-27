#!/bin/sh

echo "${0} | CONTROL_DIR=${CONTROL_DIR}"

if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable!"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi

set -x

mkdir -p ${THIRD_PARTY_DEST}
cd ${THIRD_PARTY_DEST}

unzip ${THIRD_PARTY_SOURCE}/apache-ant-1.9.2-bin.zip

tar xvfz ${THIRD_PARTY_SOURCE}/apache-jmeter-2.9.tgz

tar xvfz ${THIRD_PARTY_SOURCE}/apache-maven-3.0.5-bin.tar.gz
mv /u01/third-party/apache-maven-3.0.5/conf/settings.xml /u01/third-party/apache-maven-3.0.5/conf/settings.xml.orig
sudo ln -s ${CONTROL_DIR}/control/maven/settings.xml /u01/third-party/apache-maven-3.0.5/conf/settings.xml

tar xvfz ${THIRD_PARTY_SOURCE}/smartgithg-generic-4_6_2.tar.gz
tar xvfz ${THIRD_PARTY_SOURCE}/soapui-4.5.2-linux-bin.tar.gz

mkdir -p ${THIRD_PARTY_DEST}/hudson
cp -v ${THIRD_PARTY_SOURCE}/hudson-3.0.1.war ${THIRD_PARTY_DEST}/hudson

echo "Installing NetBeans..."
sh ${THIRD_PARTY_SOURCE}/netbeans-7.3.1-javaee-linux.sh --silent --state ${RESPONSES_SOURCE}/netbeans_responses.xml

tar xvfz ${THIRD_PARTY_SOURCE}/ideaIC-12.1.4.tar.gz --directory=/u01/third-party/

cat ${RESPONSES_SOURCE}/jxplorer_responses.txt | ${THIRD_PARTY_SOURCE}/jxplorer-3.3.02-linux-installer.run