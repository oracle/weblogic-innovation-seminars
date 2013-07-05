#!/bin/bash

export CONTENT_DIR="/u01/content/weblogic-innovation-seminars"
export TAG_NAME="wins-vbox-12.1.2"
export GIT_URL="http://github.com/oracle-weblogic/weblogic-innovation-seminars.git"

. ${DEMOS_HOME}/control/bin/winsEnv.sh > /dev/null

echo "Updating WInS Demos in ${CONTENT_DIR}..."

GIT_SYSTEM_PROXY=`git config --get --system http.proxy`
GIT_GLOBAL_PROXY=`git config --get --global http.proxy`
GIT_PROJECT_PROXY=`git config --get -f $DEMOS_HOME/../.git/config http.proxy`

echo "GIT _system_ Proxy set to: [${GIT_SYSTEM_PROXY}]"
echo "GIT _global_ Proxy set to: [${GIT_GLOBAL_PROXY}]"
echo "GIT _project_ Proxy set to: [${GIT_PROJECT_PROXY}]"

cd ${CONTENT_DIR}

git fetch --tags

if [ "$?" == "0" ]; then
  git merge ${TAG_NAME}
else
  echo "The update operation has failed.  Please check your proxy settings, especially if you are on the Oracle network.  The GIT Proxy is set to: [${GIT_GLOBAL_PROXY}\]"
fi

if [ "$1" == "wait" ]; then
  read -p "Checkout complete. Press [Enter] to close the window"
 fi