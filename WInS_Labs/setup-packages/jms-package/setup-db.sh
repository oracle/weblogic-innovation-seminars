#!/bin/bash
echo 'Creating weblogic user in DB'
sqlplus -s -l system/abcd1234@XE @$PWD/../base/sql/create_weblogic_user.sql

echo 'Creating leasing table in DB'
sqlplus -s -l weblogic/weblogic@XE @$PWD/../base/sql/leasing.ddl

