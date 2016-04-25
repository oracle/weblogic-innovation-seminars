#!/bin/sh

. /tmp/dbcs.properties

sqlplus $user/$pass@$hostname:1521/$pdbname <<EOF
 create user conference identified by conference;
 grant DBA to conference;
 exit;
EOF

sqlplus conference/conference@$hostname:1521/$pdbname <<EOF
 set define off;
 set sqlblanklines on;
 @/tmp/conference.sql
 exit;
EOF
