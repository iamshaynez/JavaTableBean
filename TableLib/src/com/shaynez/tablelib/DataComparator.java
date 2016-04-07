/**
 * DataComparator.java
 *
 */
package com.shaynez.tablelib;

import java.util.Comparator;

/**
 * @author Xiaowen, Zhang
 *
 */
public abstract class DataComparator implements Comparator<DataRow> {
	
	public static final DataComparator getDefault(final String...columnNames) {
		return new DataComparator() {
			public int compare(DataRow row1, DataRow row2) {
				for (String name : columnNames) {
					if (greaterThan(row1.get(name), row2.get(name))) {
						return 1;
					}
					if (greaterThan(row2.get(name), row1.get(name))) {
						return -1;
					}
				}
				return 0;
			}
		};
	}

	private static boolean greaterThan(String str1, String str2) {
		if (str1 == null) {
			return false;
		}
		if (str2 == null) {
			return true;
		}
		return str1.compareTo(str2) > 0;
	}
}
