/**
 * DataRow.java
 *
 */
package com.shaynez.tablelib;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Xiaowen, Zhang
 * 
 */
public class DataRow {
	
	private DataRowCollection parent;

	private String[] values;
	
	private Map<String, String> properties = new Hashtable<String, String>();
	
	DataRow() {}
	
	/**
	 * Return the row value of the index
	 * @param index
	 * @return
	 */
	public String get(int index) {
		return values[index];
	}

	/**
	 * Return the row value of the named column
	 * @param columnName
	 * @return
	 */
	public String get(String columnName) {
		if (columnName == null) {
			throw new NullPointerException("columnName");
		}

		return values[parent.getColumnIndex(columnName)];
	}

	/**
	 * Return the row value of the named column
	 * @param columnName
	 * @return
	 */
	public String get(DataColumn column) {
		if (column == null) {
			throw new NullPointerException("column");
		}

		return get(column.getName());
	}

	/**
	 * Return the row values
	 * @return
	 */
	public String[] getValues() {
		return values;
	}
	
	/**
	 * Set the row value with the index
	 * @param index
	 * @param value
	 */
	public void set(int index, String value) {
		values[index] = value;
	}
	
	/**
	 * Set the row value with the named column
	 * @param columnName
	 * @param value
	 */
	public void set(String columnName, String value) {
		if (columnName == null) {
			throw new NullPointerException("columnName");
		}
		set(parent.getColumnIndex(columnName), value);
	}

	/**
	 * Set the row value with the named column
	 * @param columnName
	 * @param value
	 */
	public void set(DataColumn column, String value) {
		if (column == null) {
			throw new NullPointerException("column");
		}

		set(column.getName(), value);
	}

	/**
	 * Set the row values
	 * @param values
	 */
	public void setValues(String[] values) {
		if (values == null) {
			throw new NullPointerException("values");
		}
		int len = Math.min(values.length, this.values.length);
		System.arraycopy(values, 0, this.values, 0, len);
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof DataRow)) {
			return false;
		}
		return Arrays.equals(((DataRow) obj).values, values);
	};
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			builder.append(i == 0 ? "" : "\t");
			builder.append(values[i]);
		}
		return builder.toString();
	};
	
	DataRow select(String...columnNames) {
		DataRow row = new DataRow();
		row.properties = properties;
		row.values = new String[columnNames.length];
		for (int i = 0; i < columnNames.length; i++) {
			row.values[i] = get(columnNames[i]);
		}
		return row;
	}
	
	DataRow copy() {
		DataRow row = new DataRow();
		row.values = new String[values.length];
		System.arraycopy(values, 0, row.values, 0, values.length);
		row.properties = properties;
		row.setParent(parent);
		return row;
	}
	
	void setParent(DataRowCollection parent) {
		this.parent = parent;
		if (values == null) {
			this.values = new String[parent.getRowSize()];
		}
	}

	/**
	 * Resize the row
	 * @param size
	 */
	void setSize(int size) {
		String[] newValues = new String[size];
		int len = Math.min(size, values.length);
		System.arraycopy(values, 0, newValues, 0, len);
		values = newValues;
	}
	
	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	public String getProperty(String name) {
		return properties.get(name);
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
}
