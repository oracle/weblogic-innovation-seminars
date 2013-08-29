#!/bin/sh

start_date=`date +%s`

killWebLogic.sh

. ${DEMOS_HOME}/control/bin/winsEnv.sh > /dev/null

echo "Removing directory ${DOMAINS}..."
rm -Rf ${DOMAINS}

cd ${DEMOS_HOME}/environment

echo "Truncating Tables..."
sqlplus '/ as sysdba' @${DEMOS_HOME}/environment/sql/truncate.sql >> /dev/null

echo "Resetting Passwords..."
sqlplus '/ as sysdba' @${DEMOS_HOME}/environment/sql/reset_passwords.sql >> /dev/null

echo "Creating Domains..."

cd ${DEMOS_HOME}/environment
mvn -P create-domain

if [ "$?" == "0" ]; then
  echo "Starting Domains..."
  mvn -P start-domain
else
  echo "PROBLEM CREATING DOMAINS!!  See messages above ^^^"
  end_date=`date +%s`
  duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)
  echo " --- rebuildDomains.sh complete (ERROR) in ${duration} minutes"

  exit 1
fi

end_date=`date +%s`
duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)

echo "rebuildDomains.sh complete (SUCCESS) in ${duration} minutes"