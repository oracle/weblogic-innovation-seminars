#!/bin/bash

export CONTENT_DIR="/u01/content/weblogic-innovation-seminars"
export TAG_NAME_FILE="~/.wins/wins_git_tag"
export TAG_NAME=`cat ${TAG_NAME_FILE}`
export GIT_URL="http://github.com/oracle-weblogic/weblogic-innovation-seminars.git"

. ${DEMOS_HOME}/control/bin/winsEnv.sh > /dev/null

echo "Using TAG_NAME=[${TAG_NAME}] that was puleld from file=[${TAG_NAME_FILE}]"
echo "Updating WInS Demos with tag=[${TAG_NAME}] in ${CONTENT_DIR}..."

GIT_SYSTEM_PROXY=`git config --get --system http.proxy`
GIT_GLOBAL_PROXY=`git config --get --global http.proxy`
GIT_PROJECT_PROXY=`git config --get -f $DEMOS_HOME/../.git/config http.proxy`

echo "GIT _system_ Proxy set to: [${GIT_SYSTEM_PROXY}] (OK to be empty)"
echo "GIT _global_ Proxy set to: [${GIT_GLOBAL_PROXY}] (OK to be empty)"
echo "GIT _project_ Proxy set to: [${GIT_PROJECT_PROXY}] (OK to be empty)"

if [ ! -e ${CONTENT_DIR} ]; then
  cd /u01/content
  git clone ${GIT_URL}
fi

cd ${CONTENT_DIR}

git fetch --tags

if [ "$?" == "0" ]; then
  git merge ${TAG_NAME}
else
  echo "The update operation has failed.  Please check your proxy settings, especially if you are on the Oracle network."
fi

if [ "$1" == "wait" ]; then
  read -p "Checkout complete. Press [Enter] to close the window"
 fi