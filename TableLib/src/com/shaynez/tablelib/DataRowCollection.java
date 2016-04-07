/**
 * DataRows.java
 *
 */
package com.shaynez.tablelib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * @author Xiaowen, Zhang
 * 
 */
public class DataRowCollection implements Iterable<DataRow> {
	
	private DataTable parent;

	private ArrayList<DataRow> rows = new ArrayList<DataRow>();

	DataRowCollection() {
	}

	public Iterator<DataRow> iterator() {
		return rows.iterator();
	}
	
	public DataRow get(int index) {
		return rows.get(index);
	}
	
	public DataRow newRow() {
		DataRow row = new DataRow();
		row.setParent(this);
		return row;
	}

	public void add(DataRow row) {
		if (row == null) {
			throw new NullPointerException("row");
		}
		rows.add(row);
		row.setParent(this);
	}

	public DataRow remove(int index) {
		return rows.remove(index);
	}

	public int size() {
		return rows.size();
	}
	
	public void clear() {
		rows.clear();
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof DataRowCollection)) {
			return false;
		}
		return ((DataRowCollection) obj).rows.equals(rows);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < rows.size(); i++) {
			builder.append(i == 0 ? "" : System.getProperty("line.separator"));
			builder.append(rows.get(i));
		}
		return builder.toString();
	}
	
	void setParent(DataTable parent) {
		this.parent = parent;
	}
	
	DataRowCollection copy() {
		DataRowCollection copy = new DataRowCollection();
		for (DataRow row : rows) {
			copy.add(row.copy());
		}
		return copy;
	}
	
	DataRowCollection select(String...columnNames) {
		DataRowCollection drc = new DataRowCollection();
		for (DataRow row : rows) {
			drc.add(row.select(columnNames));
		}
		return drc;
	}
	
	DataRowCollection where(DataFilter dataFilter) {
		DataRowCollection drc = new DataRowCollection();
		for (DataRow dataRow : rows) {
			if (dataFilter.accept(dataRow)) {
				drc.add(dataRow);
			}
		}
		return drc;
	}
	
	DataRowCollection orderBy(DataComparator comparator) {
		DataRowCollection drc = new DataRowCollection();
		drc.setParent(parent);
		for (DataRow row : rows) {
			drc.add(row);
		}
		Collections.sort(drc.rows, comparator);
		return drc;
	}
	

	DataRowCollection[] groupBy(DataComparator comparator) {
		ArrayList<DataRowCollection> groups = new ArrayList<DataRowCollection>();
		TreeMap<DataRow, Integer> treeMap = new TreeMap<DataRow, Integer>(comparator);

		for (DataRow dataRow : rows) {
			if (!treeMap.containsKey(dataRow)) {
				DataRowCollection drc = new DataRowCollection();
				drc.setParent(parent);
				groups.add(drc);
				treeMap.put(dataRow, groups.size() - 1);
			}

			int index = treeMap.get(dataRow);
			groups.get(index).add(dataRow);
		}
		return groups.toArray(new DataRowCollection[0]);
	}
	
	DataRowCollection union(DataRowCollection other) {
		DataRowCollection drc = new DataRowCollection();
		drc.setParent(parent);
		for (DataRow row : rows) {
			drc.add(row);
		}
		for (DataRow row : other.rows) {
			drc.add(row);
		}
		return drc;
	}

	int getRowSize() {
		return parent.getColumns().size();
	}

	int getColumnIndex(String name) {
		return parent.getColumnIndex(name);
	}
}
