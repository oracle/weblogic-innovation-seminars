#!/bin/sh

killWebLogic.sh

. ${DEMOS_HOME}/control/bin/winsEnv.sh > /dev/null

echo "Removing directory ${DOMAINS}..."
rm -Rf ${DOMAINS}

cd ${DEMOS_HOME}/environment

sqlplus '/ as sysdba' @${DEMOS_HOME}/environment/sql/truncate.sql >> /dev/null
sqlplus '/ as sysdba' @${DEMOS_HOME}/environment/sql/reset_passwords.sql >> /dev/null

echo "Creating Domains..."
mvn -P create-domain

if [ "$?" == "0" ]; then
  echo "Starting Domains..."
  mvn -P start-domain
fi
