#!/bin/sh

WINS_DIR="/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos"
CONTROL_DIR="${WINS_DIR}/control"

. ${CONTROL_DIR}/bin/winsEnv.sh

sqlplus 'sys/welcome1 as sysdba' @${CONTROL_DIR}/install/sql/setup-pdb.sql