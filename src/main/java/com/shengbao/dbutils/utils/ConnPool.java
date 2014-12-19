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
	
	/**
	 * 默认初始化的数据库链接数量
	 */
	private static final int DEFAULT_CONN_NUM = 10;
	
	/**
	 * 连接池的所有连接  并发安全的HashMap
	 * boolean == true 表示该连接已被使用  == false 表示该连接为空闲 当前可以使用
	 */
	public static ConcurrentHashMap<Connection, Boolean> connMap = new ConcurrentHashMap<Connection, Boolean>(DEFAULT_CONN_NUM);
	
	private static volatile ConnPool pool;//静态的实例对象引用
	
	/**
	 * 静态加载mysql驱动
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造一个默认连接数量的数据库连接池
	 * @throws SQLException
	 */
	public ConnPool() throws SQLException {
		initPool();
	}
	
	/**
	 * 初始化连接池
	 * @throws SQLException
	 */
	private void initPool() throws SQLException {
		for (int i = 0; i < DEFAULT_CONN_NUM; i++) {
			Connection conn = DriverManager.getConnection( SQLConfiguer.url + "?user="
					 + SQLConfiguer.username + "&password=" + SQLConfiguer.password
					 + "&useUnicode=true&characterEncoding=GBK");
			connMap.putIfAbsent(conn, false);
		}
	}
	
	/**
	 * 得到一条空闲的连接
	 * @return 如果有空闲的连接就获取Connection 否则返回 null 
	 */
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
	
	/**
	 * 关闭连接，把该连接置为空闲
	 * @param conn 对应的数据库连接
	 */
	public void closeConn(Connection conn) {
		if (conn != null) {
			connMap.put(conn, false);
		}
	}
	
	/**
	 * 获取一个数据库连接池实例
	 * @return
	 * @throws SQLException
	 */
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
