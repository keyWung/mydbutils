package com.shengbao.dbutils.test;

import java.sql.SQLException;
import java.util.List;

import com.shengbao.dbutils.QueryRunner;
import com.shengbao.dbutils.handlers.BeanListHandler;
import com.shengbao.dbutils.utils.ConnPool;

public class TestBeanHandler {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		QueryRunner runner = new QueryRunner();
//		BeanHandler<User> handler = new BeanHandler<User>(User.class);
//		User user = runner.query(SQLConfiguer.conn, "select * from user limit 1", handler);
		ConnPool pool = ConnPool.getInstance();
		BeanListHandler<User> listHandler = new BeanListHandler<User>(User.class);
		List<User> list = runner.query(pool.getConn(), "select * from user ", listHandler);
		for (User user : list) {
			System.out.println( " name:" + user.getName() + "age:" + user.getAge());
		}
		
	}

}
