#!/bin/bash

sqlplus system/welcome1@localhost:1521/pdb3 <<EOF
 create user trade identified by trade;
 grant DBA to trade;
 exit;
EOF

