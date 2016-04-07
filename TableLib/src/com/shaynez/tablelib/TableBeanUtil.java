/**
 * 
 */
package com.shaynez.tablelib;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author xiaowen
 *
 */
public class TableBeanUtil {
	public static DataTable getTable(ResultSet rs) throws SQLException{
		DataTable dt = new DataTable();
		
		ResultSetMetaData rsmd;

		rsmd = rs.getMetaData();

		 
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
                dt.getColumns().add(rsmd.getColumnName(i + 1));
        }
        
        while (rs.next()) {
            DataRow row = dt.newDataRow();
            for (DataColumn col : dt.getColumns()) {
                    String value = rs.getString(col.getName());
                    if (value == null) {
                            value = "";
                    }
                    row.set(col, value);
            }
            row.setProperty("source", "");
            dt.getRows().add(row);
        }
        
        return dt;
	}
}
