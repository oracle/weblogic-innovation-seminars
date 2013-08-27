#!/bin/sh

start_date=`date +%s`

#echo "Setting Oracle HTTP Proxy..."

#${DEMOS_HOME}/control/bin/setOracleProxy.sh
#. ~/setProxy.sh

mvn install:install-file -Dfile=$WL_HOME/server/lib/wls-maven-plugin.jar -DpomFile=$WL_HOME/server/lib/pom.xml

echo "Creating domains..."

${DEMOS_HOME}/control/bin/rebuildDomains.sh

if [ "$?" != "0" ]; then
  echo "Error Rebuilding domains!"
  echo " --- WInS Demo Environment Initialization complete (ERROR) in ${duration} minutes"
  exit
fi


if [ "$?" == "0" ]; then
  echo "Installing applications into domains..."
  cd ${DEMOS_HOME}
  mvn -DskipTests=true install
else
  end_date=`date +%s`
  duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)

  echo " --- WInS Demo Environment Initialization complete (ERROR) in ${duration} minutes"
fi

#if [ "$?" != "0" ]; then
#  echo "Error running Maven Sync Plugin"
#  end_date=`date +%s`
#  duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)
#
#  echo " --- WInS Demo Environment Initialization complete (ERROR) in ${duration} minutes"
#  exit
#fi

end_date=`date +%s`
duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)

echo " +++ WInS Demo Environment Initialization complete (SUCCESS) in ${duration} minutes"
