#!/bin/sh

. /tmp/dbcs.properties

sqlplus $user/$pass@$hostname:1521/$pdbname <<EOF
 drop user conference cascade;
 exit;
EOF
