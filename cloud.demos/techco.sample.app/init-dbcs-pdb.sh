#!/bin/sh

if [ -z "$1" ] || [ -z "$2" ]; then
  echo "usage: ${0} <db user> <db password> <ssh key file> <db server ip> [<PDB name>]";
  exit -1;
fi

dbuser=${1}
dbpassword=${2}
sshkey=${3}
ipaddress=${4}
#Default PDB to PDB1 if not specified
if [ -z "$5" ]; then
   pdb="PDB1";
else
  pdb=${5};
fi

cp etc/sql/create_user_template.sql etc/sql/create_user.sql
sed "s|OE_PASSWORD|${dbpassword}|g" -i etc/sql/create_user.sql

scp -i ${sshkey} etc/sql/create_user.sh   oracle@${ipaddress}:create_user.sh
scp -i ${sshkey} etc/sql/create_user.sql  oracle@${ipaddress}:create_user.sql
scp -i ${sshkey} etc/sql/oe-min-drop-create.sh  oracle@${ipaddress}:oe-min-drop-create.sh
scp -i ${sshkey} etc/sql/oe-min-drop-create.sql  oracle@${ipaddress}:oe-min-drop-create.sql
ssh -i ${sshkey} oracle@${ipaddress} "sh create_user.sh ${dbuser} ${dbpassword} ${pdb}"
ssh -i ${sshkey} oracle@${ipaddress} "sh oe-min-drop-create.sh oe ${dbpassword} ${pdb}"

rm etc/sql/create_user.sql