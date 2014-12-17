package com.shengbao.dbutils.test;

import java.sql.SQLException;

import com.shengbao.dbutils.QueryRunner;
import com.shengbao.dbutils.handlers.BeanHandler;
import com.shengbao.dbutils.utils.ConnPool;

public class TestBeanInsert {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		BeanHandler<User> handler = new BeanHandler<User>(User.class);
		ConnPool pool = ConnPool.getInstance();
		QueryRunner runner = new QueryRunner();
		User user = new User();
		user.setUserid(25);
		user.setAge(80);
		user.setName("frank");
		runner.insert(pool.getConn(), user, handler);
		
	}

}
