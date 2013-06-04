#!/bin/sh

DATE_STR=`date +"%Y%m%d_%H%M"`
LOG_PATH="${DATE_STR}"
echo LOG_PATH=$LOG_PATH

mkdir ${LOG_PATH}

cp -v ${MS1OUT} ${LOG_PATH}
cp -v ${MS1LOG}* ${LOG_PATH}

cp -v ${MS2OUT} ${LOG_PATH}
cp -v ${MS2LOG}* ${LOG_PATH}

cp -v ${ADMINOUT} ${LOG_PATH}
cp -v ${ADMINLOG}* ${LOG_PATH}


tar cvfz ${LOG_PATH}.tar.gz ${LOG_PATH}