#!/bin/sh

PROXY_SCRIPT="/home/oracle/setProxy.sh"
PROXY_MESSAGE="Proxy NOT SET for Oracle Network!!!"

echo "#!/bin/sh" > ${PROXY_SCRIPT}
echo "export http_proxy=\"\"" >> ${PROXY_SCRIPT}
echo "sudo git config --global http.proxy \"\"" >> ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}
echo "echo \"${PROXY_MESSAGE}\"" >> ${PROXY_SCRIPT}

sudo git config --global http.proxy \"\"
sudo git config http.proxy \"\"

echo "Removing Proxy Settings from GIT!"

chmod +x ${PROXY_SCRIPT}

if [ "$1" == "wait" ]; then
  echo "This window will close automatically in 5s..."
  sleep 5
 fi
