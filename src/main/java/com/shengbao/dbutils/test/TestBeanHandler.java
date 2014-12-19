package com.shengbao.dbutils.test;

import java.sql.SQLException;
import java.util.List;
import com.shengbao.dbutils.QueryRunner;
import com.shengbao.dbutils.handlers.BeanHandler;
import com.shengbao.dbutils.handlers.BeanListHandler;
import com.shengbao.dbutils.utils.ConnPool;
/**
 * 测试从数据库查询一个或多个bean出来
 * @author shengbao
 *
 */
public class TestBeanHandler {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		QueryRunner runner = new QueryRunner();
		
		//测试将一个结果封装成单个User
		BeanHandler<User> handler = new BeanHandler<User>(User.class);
		ConnPool pool = ConnPool.getInstance();
		User user = runner.query(pool.getConn(), "select * from user  limit 1", handler);
		System.out.println("test one &&&" +  " name: " + user.getName() + "age: " + user.getAge());
		
		//测试将多行结果集封装成List<User>
		BeanListHandler<User> listHandler = new BeanListHandler<User>(User.class);
		List<User> list = runner.query(pool.getConn(), "select * from user ", listHandler);
		for (User u : list) {
			System.out.println( " name:" + u.getName() + "age:" + u.getAge());
		}
		
	}

}
