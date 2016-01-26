#!/bin/bash

sudo docker stop wlsadmin2;
sudo docker rm $(sudo docker ps -a -q)
sudo docker rmi samplewls:12.2.1
sudo docker rmi samplewls2:12.2.1

sqlplus system/welcome1@localhost:1521/pdborcl <<EOF
 drop user cdtr cascade;
 exit;
EOF

sqlplus system/welcome1@localhost:1521/pdb2 <<EOF
 drop user cdtr cascade;
 exit;
EOF

