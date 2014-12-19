package com.shengbao.dbutils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.shengbao.dbutils.handlers.ModifierHandler;
import com.shengbao.dbutils.handlers.ResultSetHandler;
import com.shengbao.dbutils.utils.StatementPool;

/**
 * 框架引擎  对象类  操作入口类
 * 集查询和修改操作于一身的类
 * @author shengbao
 * @since 1.0
 */
public class QueryRunner {
	
	/**
	 * 查询数据库返回一个对象
	 * 
	 * @param conn
	 * @param sql
	 * @param handler
	 * @return
	 * @throws SQLException
	 */
	public <T> T query(Connection conn, String sql, ResultSetHandler<?> handler) throws SQLException {
		Statement stat = StatementPool.getState(conn);
		ResultSet rs = this.getResultSet(stat,sql);
		return handler.handle(rs);
	}
	
	/**
	 * 向数据库插入一个对象
	 * 
	 * @return
	 * @throws SQLException
	 */
	public <T> T insert(Connection conn, T bean, ModifierHandler<?> handler) throws SQLException {
		return handler.insert(conn,bean);
	}
	
	/**
	 * 得到结果集
	 * 
	 * @param stat
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	private ResultSet getResultSet(Statement stat, String sql) throws SQLException {
		return stat.executeQuery(sql);
	}
}
