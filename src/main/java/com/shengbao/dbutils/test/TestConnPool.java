package com.shengbao.dbutils.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.shengbao.dbutils.utils.ConnPool;
/**
 * 测试
 * @author Administrator
 *
 */
public class TestConnPool {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		ConnPool pool = ConnPool.getInstance();
		Connection connection = pool.getConn();
		System.out.println(connection);
	}

}
