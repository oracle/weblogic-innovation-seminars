#!/bin/bash

export CONTENT_DIR="/u01/content/weblogic-innovations-seminars"
export TAG_NAME="wins-vbox"
export GIT_URL="http://github.com/oracle-weblogic/weblogic-innovations-seminars.git"

. ${DEMOS_HOME}/control/bin/winsEnv.sh > /dev/null

echo "Updating WInS Demos in ${CONTENT_DIR}..."

GIT_PROXY=`git config --get http.proxy`

echo "GIT Proxy set to: \"${GIT_PROXY}\""

cd ${CONTENT_DIR}

git fetch --tags

if [ "$?" == "0" ]; then
  git merge ${TAG_NAME}
else
  echo "The update operation has failed.  Please check your proxy settings, especially if you are on the Oracle network.  The GIT Proxy is set to: \"${GIT_PROXY}\""
fi

if [ "$1" == "wait" ]; then
  read -p "Checkout complete. Press [Enter] to close the window"
 fi

#foo