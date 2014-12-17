package com.shengbao.dbutils.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.shengbao.dbutils.SQLConfiguer;

/**
 * 一个简陋的mysql连接池
 * @author shengbao
 *
 */
public class ConnPool {
	
	private static final int DEFAULT_CONN_NUM = 10;
	
	public static ConcurrentHashMap<Connection, Boolean> connMap = new ConcurrentHashMap<Connection, Boolean>(DEFAULT_CONN_NUM);
	
	private static volatile ConnPool pool;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ConnPool() throws SQLException {
		initPool();
	}
	
	private void initPool() throws SQLException {
		for (int i = 0; i < DEFAULT_CONN_NUM; i++) {
			Connection conn = DriverManager.getConnection( SQLConfiguer.url + "?user="
					 + SQLConfiguer.username + "&password=" + SQLConfiguer.password
					 + "&useUnicode=true&characterEncoding=GBK");
			connMap.putIfAbsent(conn, false);
		}
	}
	
	public Connection getConn() {
		for (Entry<Connection, Boolean> m : connMap.entrySet()) {
			if (m.getValue() == false) {
				Connection conn = m.getKey();
				connMap.put(conn, true);
				return conn;
			}
		}
		return null;
	}
	
	public void closeConn(Connection conn) {
		if (conn != null) {
			connMap.put(conn, false);
		}
	}
	
	public static ConnPool getInstance() throws SQLException {
		if (pool == null) {
			synchronized (ConnPool.class) {
				if (pool == null) {
					pool = new ConnPool();
				}
			}
		}
		return pool;
	}
	
}
