#!/bin/sh

echo "Creating domains..."

${DEMOS_HOME}/control/bin/rebuildDomains.sh

if [ "$?" == "0" ]; then
  echo "Installing applications into domains..."
  cd ${DEMOS_HOME}
  mvn -DskipTests=true install
fi

mvn install:install-file -Dfile=/u01/wls1212/wlserver/modules/com.bea.core.weblogic.web.api_3.0.0.0.jar -DgroupId=com.bea.core.weblogic.web -DartifactId=api -Dversion=12.1.2.0 -Dpackaging=jar

echo "Initialization complete!!!"
