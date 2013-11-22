package com.oracle.wins.pbd;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import weblogic.jdbc.extensions.WLDataSource;

/**
 * @author pnagy
 *
 */
@ManagedBean(name = "department")
@SessionScoped
public class DepartmentBean implements Serializable {

	private DataSource ds;

	public DepartmentBean() throws SQLException {

		if (ds == null) {
			Hashtable ht = new Hashtable();

			ht.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
			ht.put(Context.SECURITY_PRINCIPAL, "weblogic");
			ht.put(Context.SECURITY_CREDENTIALS, "welcome1");
			
			try {
				InitialContext ctx = new InitialContext(ht);
				ds = (DataSource) ctx.lookup("pdbds");
			} catch (NamingException e) {
				e.printStackTrace();
				throw new SQLException(
						"Can't get data source: pdbds (create it properly according to the lab guide)");
			}
		}

		((WLDataSource) ds).removeConnectionLabelingCallback();

		PDBConnectionLabelingCallback callback = new PDBConnectionLabelingCallback();
		((WLDataSource) ds).registerConnectionLabelingCallback(callback);

	}

	// connect to PDB and get customer list
	public List<Department> getDepartmentList(String pdb) throws Exception {

		Properties props = new Properties();
		props.put(com.oracle.wins.pbd.PDBConnectionLabelingCallback.TENANT, pdb);
		Connection con = ((WLDataSource) ds).getConnection(props);
		
		if (con == null)
			throw new Exception("Can't get database connection");

		List<Department> list = new ArrayList<Department>();;
		try {
			PreparedStatement ps = con
					.prepareStatement("select department_id, name, address from hr.department");

			// get department data from database
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				Department dept = new Department();

				dept.setDepartmentID(result.getLong("department_id"));
				dept.setName(result.getString("name"));
				dept.setAddress(result.getString("address"));

				// store all data into a List
				list.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Department dept = new Department();
			dept.setName("Can't execute SQL statement! See log for more details.");
			dept.setAddress(e.getSQLState() + " " + e.getMessage());
			list.add(dept);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				
			}
		}
		return list;
	}

}