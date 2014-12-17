package com.shengbao.dbutils.processor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 基本的行处理器  
 * @author shengbao
 *
 */
public class BasicRowProcessor implements RowProcessor{
	
	/**
	 * 对象处理器 
	 */
	private static final BeanProcessor beanProcessor = new BeanProcessor();
	
	/**
	 * 将一行结果集 映射成一个对象
	 * @param rs 查询到的结果集
	 * @param clazz 要映射的目标对象的类型
	 * @throws SQLException 
	 */
	public <T> T toBean(ResultSet rs, Class<T> clazz) throws SQLException {
		return beanProcessor.toBean(rs,clazz);//返回目标对象
	}
	
	/**
	 * 插入对象到数据库
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public <T> T insert(Connection conn, Class<?> clazz, T bean) throws SQLException {
		return beanProcessor.beanToRow(conn,clazz,bean);
	}
	
	
}
