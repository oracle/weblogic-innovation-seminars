#!/bin/bash
cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8

sqlplus system/welcome1@localhost:1521/pdborcl <<EOF
 drop user petstore cascade;

 create user petstore identified by petstore;
 grant DBA to petstore;
 exit;
EOF

sqlplus petstore/petstore@localhost:1521/pdborcl <<EOF
 @DBCS/petstore.sql
 exit;
EOF

