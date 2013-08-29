#!/bin/sh
sudo service oracle-12c-cdb start
sudo service oracle-12c-pdb start

if [ "$1" == "wait" ]; then
  echo "This window will close automatically in 5s..."
  sleep 5
 fi
