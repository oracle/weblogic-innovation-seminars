package com.oracle.wins.pbd;

/**
 * @author pnagy
 *
 */
public class Department {

	public long departmentID;
	public String name;
	public String address;
	
	public long getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(long departmentID) {
		this.departmentID = departmentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
