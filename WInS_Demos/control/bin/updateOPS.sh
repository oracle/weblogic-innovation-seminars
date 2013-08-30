#!/bin/bash

export CONTENT_DIR="/u01/content/oracle-parcel-service"
export TAG_NAME_FILE="/home/oracle/.wins/ops_git_tag"
export TAG_NAME=`cat ${TAG_NAME_FILE}`
export GIT_URL="http://github.com/jeffreyawest/oracle-parcel-service.git"

. ${DEMOS_HOME}/control/bin/winsEnv.sh > /dev/null

echo "Using TAG_NAME=[${TAG_NAME}] that was puleld from file=[${TAG_NAME_FILE}]"
echo "Updating Oracle Parcel Service with tag=[${TAG_NAME}] in ${CONTENT_DIR}..."

GIT_PROXY=`git config --get http.proxy`

echo "GIT Proxy set to: \"${GIT_PROXY}\""

if [ ! -e ${CONTENT_DIR} ]; then
  cd /u01/content
  git clone ${GIT_URL}
fi

cd ${CONTENT_DIR}

git fetch --tags

if [ "$?" == "0" ]; then
    git merge ${TAG_NAME}
  else
    echo "The update operation has failed.  Please check your proxy settings, especially if you are on the Oracle network.  The GIT Proxy is set to: ${GIT_PROXY}"
fi

if [ "$1" == "wait" ]; then
  read -p "Checkout complete. Press [Enter] to close the window"
 fi
