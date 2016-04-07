/**
 * DataTable.java
 *
 */
package com.shaynez.tablelib;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Xiaowen, Zhang
 * 
 */
public class DataTable {

	
	private String name;

	private DataColumnCollection cols;

	private DataRowCollection rows;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private DataTable(DataColumnCollection cols, DataRowCollection rows) {
		this.cols = cols;
		this.rows = rows;
		cols.setParent(this);
		rows.setParent(this);
	}

	/**
	 * Create a new instance of the DataTable
	 */
	public DataTable() {
		this(new DataColumnCollection(), new DataRowCollection());
	}

	/**
	 * Create a new instance of the DataTable with a name.
	 * 
	 * @param name
	 */
	public DataTable(String name) {
		this();
		this.name = name;
	}

	/**
	 * Create a new DataRow of the DataTable
	 * 
	 * @return
	 */
	public DataRow newDataRow() {
		return rows.newRow();
	}

	public void loadValues(String...values) {
		DataRow row = rows.newRow();
		row.setValues(values);
		rows.add(row);
	}

	/**
	 * Clear the data
	 */
	public void clear() {
		rows.clear();
	}

	/**
	 * Return the DataColumns in the DataView
	 * 
	 * @return
	 */
	public DataColumnCollection getColumns() {
		return cols;
	}

	/**
	 * Return the DataRows in the DataView
	 * 
	 * @return
	 */
	public DataRowCollection getRows() {
		return rows;
	}
	
	public DataTable select(String...columnNames) {
		DataColumnCollection dcc = cols.select(columnNames);
		DataRowCollection drc = rows.select(columnNames);
		return new DataTable(dcc, drc);
	}

	public DataTable where(DataFilter filter) {
		if (filter == null) {
			return this;
		}
		DataRowCollection drc = rows.where(filter);
		return new DataTable(cols, drc);
	}

	public DataTable orderBy(DataComparator comparator) {
		DataRowCollection drc = rows.orderBy(comparator);
		return new DataTable(cols, drc);
	}
	
	public DataTable orderBy(String... columnNames) {
		return orderBy(DataComparator.getDefault(columnNames));
	}
	
	public DataTable[] groupBy(DataComparator comparator) {
		ArrayList<DataTable> result = new ArrayList<DataTable>();
		for (DataRowCollection c : rows.groupBy(comparator)) {
			result.add(new DataTable(cols, c));
		}
		return result.toArray(new DataTable[0]);
	}

	public DataTable[] groupBy(String... columnNames) {
		return groupBy(DataComparator.getDefault(columnNames));
	}

	public DataTable union(DataTable table) {
		if (!table.cols.equals(this.cols)) {
			throw new IllegalArgumentException("Table are with different structure.");
		}
		DataRowCollection drc = rows.union(table.rows);
		return new DataTable(cols, drc);
	}

	public DataTable copy() {
		return new DataTable(cols.copy(), rows.copy());
	}

	public boolean isEmpty(){
		return this.rows.size() <= 0;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof DataTable)) {
			return false;
		}
		DataTable dt = (DataTable) obj;
		return dt.cols.equals(cols) && dt.rows.equals(rows);
	}
	
	/**
	 * Remove the DataRow from data table if each field in the row has same value.  
	 * @param row the row to remove.
	 */
	public void remove(DataRow row){
	    for (Iterator<DataRow> it = rows.iterator();it.hasNext();){
		DataRow _row =  it.next();
		if (_row.equals(row)){
		    it.remove();
		}
	    }
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(cols);
		builder.append(System.getProperty("line.separator"));
		builder.append(rows);
		builder.append(System.getProperty("line.separator"));
		return builder.toString();
	}
	
	public boolean hasCol(String col){
		return this.cols.contains(col);
	}

	int getColumnIndex(String name) {
		return cols.get(name).getIndex();
	}

	void setSize(int size) {
		for (DataRow row : rows) {
			row.setSize(size);
		}		
	}
	
	
}
