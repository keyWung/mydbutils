package com.shengbao.dbutils.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Statement 池  
 * @author shengbao
 *
 */
public class StatementPool {
	private static HashMap<Connection, Statement> statMap = new HashMap<Connection, Statement>();
	
	/**
	 * 获取某个连接的陈述
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static Statement getState(Connection conn) throws SQLException {
		Statement stat = statMap.get(conn);
		if (stat == null) {
			statMap.put(conn, conn.createStatement());
		}
		return statMap.get(conn);
	}
	
}
