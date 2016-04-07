/**
 * BaseFilter.java
 *
 */
package com.shaynez.tablelib;


/**
 * @author Xiaowen, Zhang
 *
 */
public abstract class DataFilter {
	
	/**
	 * Return true if the dataRow passed the test 
	 * @param dataRow the dataRow to be tested.
	 * @return test result.
	 */
	public abstract boolean accept(final DataRow row);
	
	/**
	 * Return the negative filter of filter.
	 * @return
	 */
	public static DataFilter not(final DataFilter filter) {
		return new DataFilter() {
			public boolean accept(DataRow row) {
				if (filter == null) {
					return false;
				}
				return !filter.accept(row);
			}
		};
	}

	/**
	 * Return the and combination of this and otherFilter.
	 * @param otherFilter
	 * @return
	 */
	public DataFilter and(final DataFilter otherFilter) {
		return new DataFilter() {
			public boolean accept(DataRow row) {
				if (otherFilter == null) {
					return DataFilter.this.accept(row);
				}
				return DataFilter.this.accept(row) && otherFilter.accept(row);
			}
		};
	}
	
	/**
	 * Return the or combination of this and otherFilter.
	 * @param otherFilter
	 * @return
	 */
	public DataFilter or(final DataFilter otherFilter) {
		return new DataFilter() {
			public boolean accept(DataRow row) {
				if (otherFilter == null) {
					return DataFilter.this.accept(row);
				}
				return DataFilter.this.accept(row) || otherFilter.accept(row);
			}
		};
	}
}
