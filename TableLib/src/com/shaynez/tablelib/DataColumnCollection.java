/**
 * DataColumns.java
 *
 */
package com.shaynez.tablelib;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Xiaowen, Zhang
 * 
 */
public class DataColumnCollection implements Iterable<DataColumn> {

	private ArrayList<DataColumn> cols = new ArrayList<DataColumn>();
	
	private DataTable parent;

	DataColumnCollection() {
	}
	
	public Iterator<DataColumn> iterator() {
		return cols.iterator();
	}
	
	public DataColumn get(int index) {
		return cols.get(index);
	}
	
	public DataColumn get(String name) {
		for (DataColumn col : cols) {
			if (col.getName().equals(name)) {
				return col;
			}
		}
		throw new IndexOutOfBoundsException(name);
	}

	public void add(DataColumn col) {
		if (cols.indexOf(col) != -1) {
			throw new IllegalArgumentException("DataColumn already exist: " + col.getName());
		}
		cols.add(col);
		col.setParent(this);
	}
	
	public void add(String name) {
		DataColumn col = new DataColumn(name);
		add(col);
		parent.setSize(cols.size());
	}

	public int size() {
		return cols.size();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof DataColumnCollection)) {
			return false;
		}
		return cols.equals(((DataColumnCollection) obj).cols);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cols.size(); i++) {
			builder.append(i == 0 ? "" : "\t");
			builder.append(cols.get(i));
		}
		return builder.toString();
	}
	
	
	public boolean contains(String col){
		for (DataColumn column : cols){
			if (column.getName().equals(col)){
				return true;
			}
		}
		return false;
	}
	
	void setParent(DataTable parent) {
		this.parent = parent;
	}

	int getIndex(DataColumn col) {
		return cols.indexOf(col);
	}
	
	DataColumnCollection select(String...columnNames) {
		DataColumnCollection dcc = new DataColumnCollection();
		for (String columnName : columnNames) {
			dcc.add(get(columnName).copy());
		}
		return dcc;
	}
	
	DataColumnCollection copy() {
		DataColumnCollection copy = new DataColumnCollection();
		for (DataColumn col : cols) {
			copy.add(col.copy());
		}
		return copy;
	}
	
	
}
