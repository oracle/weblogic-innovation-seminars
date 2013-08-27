#!/bin/sh

echo "${0} / CONTROL_DIR=${CONTROL_DIR}"


export SOFTWARE_SOURCE="/software/oracle"
export ORA_INVENTORY="/etc/oraInst.loc"
export JAVA_HOME="/usr/java/latest"
export JAVA="${JAVA_HOME}/bin/java"

#export WINS_DIR="/u01/content/weblogic-innovation-seminars/WInS_Demos/"
#export WINS_DIR="/media/sf_oracle-weblogic/weblogic-innovation-seminars/WInS_Demos"

#export CONTROL_DIR="${WINS_DIR}/control"
export RESPONSES_SOURCE="${CONTROL_DIR}/install/responses"

export ORACLE_HOME="/u01/app/oracle/product/12.1/database"

export THIRD_PARTY_SOURCE="/software/third-party"
export THIRD_PARTY_DEST="/u01/third-party"