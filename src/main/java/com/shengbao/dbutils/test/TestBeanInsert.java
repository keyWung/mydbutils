package com.shengbao.dbutils.test;

import java.sql.SQLException;

import com.shengbao.dbutils.QueryRunner;
import com.shengbao.dbutils.handlers.BeanHandler;
import com.shengbao.dbutils.utils.ConnPool;
/**
 * 测试插入一个bean对象到数据库
 * @author shengbao
 *
 */
public class TestBeanInsert {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		QueryRunner runner = new QueryRunner();
		//测试插入一个javabean对象到数据库 javaBean属性必须完全和数据库一致
		BeanHandler<User> handler = new BeanHandler<User>(User.class);
		ConnPool pool = ConnPool.getInstance();
		User user = new User();
		user.setUserid(25);
		user.setAge(80);
		user.setName("frank");
		
		runner.insert(pool.getConn(), user, handler);
		
	}

}
