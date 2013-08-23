#!/bin/sh

set -v

THIRD_PARTY_SOURCE="/software/third-party"
THIRD_PARTY_DEST="/u01/third-party"
RESPONSES_SOURCE="/u01/content/weblogic-innovation-seminars/WInS_Demos/control/install/responses"

mkdir -p ${THIRD_PARTY_DEST}
cd ${THIRD_PARTY_DEST}

unzip ${THIRD_PARTY_SOURCE}/apache-ant-1.9.2-bin.zip

tar xvfz ${THIRD_PARTY_SOURCE}/apache-jmeter-2.9.tgz
tar xvfz ${THIRD_PARTY_SOURCE}/apache-maven-3.0.5-bin.tar.gz
tar xvfz ${THIRD_PARTY_SOURCE}/smartgithg-generic-4_6_2.tar.gz
tar xvfz ${THIRD_PARTY_SOURCE}/soapui-4.5.2-linux-bin.tar.gz

sudo rpm -Uvh ${THIRD_PARTY_SOURCE}/hudson-3.0.1.b2-1.1.noarch.rpm

mkdir -p ${THIRD_PARTY_DEST}/hudson
cp -v ${THIRD_PARTY_SOURCE}/hudson-3.0.1.war ${THIRD_PARTY_DEST}/hudson

echo "Installing NetBeans..."
sh ${THIRD_PARTY_SOURCE}/netbeans-7.3.1-javaee-linux.sh --silent --state ${RESPONSES_SOURCE}/netbeans_responses.xml

tar xvfz ${THIRD_PARTY_SOURCE}/ideaIC-12.1.4.tar.gz --directory=/u01/third-party/

${THIRD_PARTY_SOURCE}/jxplorer-3.3.02-linux-installer.run