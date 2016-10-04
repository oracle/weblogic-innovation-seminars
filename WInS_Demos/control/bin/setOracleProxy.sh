#!/bin/sh

ORACLE_HTTP_PROXY="http:\/\/www-proxy.us.oracle.com:80"

echo "Set Maven proxy settings"
cp /u01/content/weblogic-innovation-seminars/WInS_Demos/control/maven/settings.xml ~/.m2/

#=========================================================
YUMCONF_FILE=/etc/yum.conf

grepresult=$(grep -c "proxy=$ORACLE_HTTP_PROXY" $YUMCONF_FILE -s)

if [ $grepresult == 1 ]
then
    # yum configured for proxy, need to delete
    echo "yum is already configured for proxy."
else
    # yum not configured for proxy, need to add
    sudo sed -i '$ a\'"proxy=$ORACLE_HTTP_PROXY"'' $YUMCONF_FILE
    echo "yum now has been configured for proxy."
fi
#=========================================================

ORACLE_HTTP_PROXY="http://www-proxy.us.oracle.com:80"
PROXY_SCRIPT="/home/oracle/setProxy.sh"
PROXY_MESSAGE="Proxy Configured for Oracle Network!!!"

echo "#!/bin/sh" > ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}

sudo git config --system http.proxy ${ORACLE_HTTP_PROXY}

echo -e "export http_proxy=${ORACLE_HTTP_PROXY}\nexport https_proxy=${ORACLE_HTTP_PROXY}" > /home/oracle/setproxy.sh

sudo cp /home/oracle/setproxy.sh /etc/profile.d

echo ${PROXY_MESSAGE} 

sudo git config --system --get http.proxy

chmod +x ${PROXY_SCRIPT}

if [ "$1" == "wait" ]; then
  echo "This window will close automatically in 5s..."
  sleep 5
 fi