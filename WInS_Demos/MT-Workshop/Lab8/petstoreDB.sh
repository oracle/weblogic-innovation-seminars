#!/bin/bash
cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8

sqlplus system/welcome1@localhost:1521/pdborcl <<EOF
 create user petstore identified by petstore;
 grant DBA to petstore;
 exit;
EOF

sqlplus petstore/petstore@localhost:1521/pdborcl <<EOF
 @petstore.sql
 exit;
EOF
