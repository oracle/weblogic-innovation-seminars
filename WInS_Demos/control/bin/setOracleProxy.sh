#!/bin/sh

PROXY_SCRIPT="/home/oracle/setProxy.sh"
ORACLE_HTTP_PROXY="http://www-proxy.us.oracle.com:80"
PROXY_MESSAGE="Proxy Configured for Oracle Network!!!"

echo "#!/bin/sh" > ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}

sudo git config --global http.proxy ${ORACLE_HTTP_PROXY}
sudo git config http.proxy ${ORACLE_HTTP_PROXY}

echo ${PROXY_MESSAGE}

chmod +x ${PROXY_SCRIPT}

if [ "$1" == "wait" ]; then
  echo "This window will close automatically in 5s..."
  sleep 5
 fi
