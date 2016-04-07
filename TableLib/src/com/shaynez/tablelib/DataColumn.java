/**
 * DataColumn.java
 *
 */
package com.shaynez.tablelib;

/**
 * @author Xiaowen, Zhang
 * 
 */
public class DataColumn {

	private DataColumnCollection parent;

	private String name;

	public String getName() {
		return name;
	}

	public int getIndex() {
		if (parent != null) {
			return parent.getIndex(this);
		}
		return 0;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public DataColumn(String name) {
		if (name == null) {
			throw new NullPointerException("name");
		}
		this.name = name;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof DataColumn)) {
			return false;
		}
		return name.equals(((DataColumn) obj).name);
	}
	
	public String toString() {
		return name;
	}

	void setParent(DataColumnCollection parent) {
		this.parent = parent;
	}
	
	DataColumn copy() {
		DataColumn col = new DataColumn(name);
		col.setParent(parent);
		return col;
	}

}
