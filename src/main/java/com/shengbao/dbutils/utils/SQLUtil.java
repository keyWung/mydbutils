package com.shengbao.dbutils.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUtil {
	
	
	public static ResultSet getResultSet(Statement stat, String sql) throws SQLException {
		ResultSet rs = stat.executeQuery(sql);
		return rs;
	}
	
}
