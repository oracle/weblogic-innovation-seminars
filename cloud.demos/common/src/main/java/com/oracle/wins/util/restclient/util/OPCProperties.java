package com.oracle.wins.util.restclient.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class OPCProperties {
	
	public static final String GOAL_JCS_GET_INSTANCE_DETAILS = "jcs-get-instance-details";
	public static final String GOAL_DBCS_GET_INSTANCE_DETAILS = "dbcs-get-instance-details";
	public static final String GOAL_JCS_INSTANCE_DELETE = "jcs-delete";
	public static final String GOAL_DBCS_INSTANCE_DELETE = "dbcs-delete";
	public static final String GOAL_DBCS_INSTANCE_CREATE = "dbcs-create";
	public static final String GOAL_JCS_INSTANCE_CREATE = "jcs-create";
	public static final String GOAL_JCS_GET_SPECIFIC_JOB_DETAILS = "jcs-get-job-details";
	
	public static final String GOAL_STORAGE_DETAILS = "storage-get-details";
	public static final String GOAL_STORAGE_CREATE = "storage-create";
	public static final String GOAL_STORAGE_LIST = "storage-list";
	public static final String GOAL_STORAGE_DELETE = "storage-delete";
	
	public static final String CONTENT_TYPE_VND_SERVICE_JSON = "application/vnd.com.oracle.oracloud.provisioning.Service+json";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_TEXT = "text/plain";
	
	public static final String OPC_BASE_URL = "opc.base.url";
	public static final String OPC_USERNAME = "opc.username";
	public static final String OPC_PASSWORD = "opc.password";
	public static final String OPC_IDENTITY_DOMAIN = "opc.identity.domain";
	public static final String OPC_STORAGE_CONTAINER = "opc.storage.container";
	public static final String OPC_STORAGE_GENERIC_URL = "opc.storage.generic.url";
		
	public static final String SSH_PUBLIC_KEY = "ssh.public.key";

	public static final String JCS_REST_URL = "jcs.rest.url";
	public static final String JCS_INSTANCE_VERSION_1 = "jcs.instance.version.1";
	public static final String JCS_INSTANCE_OTD_1 = "jcs.instance.otd.1";
	public static final String JCS_INSTANCE_1 = "jcs.instance.1";
	public static final String JCS_INSTANCE_DESC_1 = "jcs.instance.desc.1";
	public static final String JCS_INSTANCE_ADMIN_USER_1 = "jcs.instance.admin.user.1";
	public static final String JCS_INSTANCE_ADMIN_PASSWORD_1 = "jcs.instance.admin.password.1";

	public static final String DBCS_BASE_URL = "dbcs.base.url";
	public static final String DBCS_REST_URL = "dbcs.rest.url";
	public static final String DBCS_INSTANCE_1 = "dbcs.instance.1";
	public static final String DBCS_INSTANCE_DESC_1 = "dbcs.instance.desc.1";
	public static final String DBCS_INSTANCE_SID_1 = "dbcs.instance.sid.1";
	public static final String DBCS_INSTANCE_PDB1_1 = "dbcs.instance.pdb1.1";
	public static final String DBCS_INSTANCE_VERSION_1 = "dbcs.instance.version.1";
	public static final String DBCS_INSTANCE_USABLE_STORAGE_1 = "dbcs.instance.usable.storage.1";
	
	public static final String DBCS_DBA_NAME = "dbcs.dba.name";
	public static final String DBCS_DBA_PASSWORD = "dbcs.dba.password";
	
	public static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";
	public static final String HEADER_X_STORAGE_URL = "X-Storage-Url";
	
	private final Properties configProp = new Properties();

	private OPCProperties() {
		// Private constructor to restrict new instances
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"environment.properties");

		System.out.println("Read all properties from file");
		try {
			configProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class LazyHolder {
		private static final OPCProperties INSTANCE = new OPCProperties();
	}

	public static OPCProperties getInstance() {
		return LazyHolder.INSTANCE;
	}

	public String getProperty(String key) {
		//System.out.println("Property: " + key + "="
			//	+ configProp.getProperty(key));
		return configProp.getProperty(key);
	}

	public Set<String> getAllPropertyNames() {
		return configProp.stringPropertyNames();
	}

	public boolean containsKey(String key) {
		return configProp.containsKey(key);
	}
}
