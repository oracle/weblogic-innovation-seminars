package com.oracle.wins.pbd;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import oracle.ucp.jdbc.ConnectionLabelingCallback;
import oracle.ucp.jdbc.LabelableConnection;

public class PDBConnectionLabelingCallback implements
		ConnectionLabelingCallback {

	public static final String TENANT = "TENANT";

	public int cost(Properties reqLabels, Properties currentLabels) {
		// Case 1: exact match
		if (reqLabels.equals(currentLabels)) {
			System.out.println("## Exact match found = "
					+ (String) reqLabels.get(TENANT));
			return 0;
		}
		// Case 2: some labels match with no unmatched labels
		String val1 = (String) reqLabels.get(TENANT);
		String val2 = (String) currentLabels.get(TENANT);
		// No label matches to application's preference.
		// Do not choose this connection.
		System.out.println("## No match '" + val1 + "' != '" + val2 + "'##");
		return Integer.MAX_VALUE;
	}

	public boolean configure(Properties reqLabels, Object conn) {
		// Only called if not a match
		try {
			String valStr = (String) reqLabels.get(TENANT);
			Statement s = ((Connection) conn).createStatement();
			s.executeUpdate("ALTER SESSION SET CONTAINER = " + valStr);
			LabelableConnection lconn = (LabelableConnection) conn;
			System.out.println("## In configure for value " + valStr);
			lconn.applyConnectionLabel(TENANT, valStr);
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
		return true;
	}

	public java.util.Properties getRequestedLabels() {
		Properties props = new Properties();
		props.put(TENANT, "value");
		return props;
	}

}
