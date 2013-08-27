#!/bin/sh

echo "${0} | CONTROL_DIR=${CONTROL_DIR}"

if [ -z "${CONTROL_DIR}" ]; then
  echo "Please set CONTROL_DIR variable!"
  exit 1
else
  . ${CONTROL_DIR}/install/installEnv.sh
  . ${CONTROL_DIR}/install/util-functions.sh --source-only
fi


if [ "$?" != "0" ];
then
	echo "Error setting env!"
	exit 1
fi

cd $DEMOS_HOME

mvn compile verify

if [ "$?" != "0" ];
then
	echo "Error on compile+verify!"
	exit 1
fi

./initialize.sh

if [ "$?" != "0" ];
then
	echo "Error Running initialize!"
	exit 1
fi

${CONTROL_DIR}/bin/killWebLogic.sh

rm -Rf $DOMAINS