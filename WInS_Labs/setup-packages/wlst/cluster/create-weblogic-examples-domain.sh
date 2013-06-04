
if [ -z "${WL_HOME}" ]; then
	echo "WL_HOME not set"
	export WL_HOME="/u01/wls1211/wlserver_12.1"
fi

${WL_HOME}/common/bin/wlst.sh create_domain.py -loadProperties weblogic-examples-domain.properties
