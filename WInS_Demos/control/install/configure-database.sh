#!/bin/sh

echo "${0} | CONTROL_DIR=${CONTROL_DIR}"

if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable!"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi

${ORACLE_HOME}/bin/sqlplus //wins-vbox:1521:pdborcl 'sys/welcome1 as sysdba' @${CONTROL_DIR}/install/sql/setup-pdb.sql