package com.shengbao.dbutils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.shengbao.dbutils.utils.StatementPool;

/**
 * 查询引擎
 * @author shengbao
 *
 */
public class QueryRunner {
	
	public <T> T query(Connection conn, String sql, ResultSetHandler<?> handler) throws SQLException {
		Statement stat = StatementPool.getState(conn);
		ResultSet rs = this.getResultSet(stat,sql);
		return handler.handle(rs);
	}
	
	private ResultSet getResultSet(Statement stat, String sql) throws SQLException {
		return stat.executeQuery(sql);
	}
}
