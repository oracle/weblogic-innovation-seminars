#!/bin/sh

echo "Setting Oracle HTTP Proxy..."

${DEMOS_HOME}/control/bin/setOracleProxy.sh
. ~/setProxy.sh

echo "Creating domains..."

${DEMOS_HOME}/control/bin/rebuildDomains.sh
${DEMOS_HOME}/control/bin/rebuildDomains.sh

if [ "$?" == "0" ]; then
  echo "Installing applications into domains..."
  cd ${DEMOS_HOME}
  mvn -DskipTests=true install
fi

cd

echo "Initialization complete!!!"
