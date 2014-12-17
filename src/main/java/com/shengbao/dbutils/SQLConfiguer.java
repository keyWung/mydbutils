package com.shengbao.dbutils;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConfiguer {
	public static final String url = "jdbc:mysql://127.0.0.1:3306/infosys";
	public static final String username = "root";
	public static final String password = "123456";
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static java.sql.Connection conn;
	public static java.sql.Statement stat;
	public static java.sql.PreparedStatement preStat;
	
	/**
	 * 先使用默认这个 用来测试 后面再自己实现个数据库连接池
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( url + "?user="
					 + username + "&password=" + password
					 + "&useUnicode=true&characterEncoding=GBK");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
