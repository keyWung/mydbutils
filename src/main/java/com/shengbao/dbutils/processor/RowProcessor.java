package com.shengbao.dbutils.processor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 行处理器接口
 * @author shengbao
 *
 */
public interface RowProcessor {
	
	/**
	 * 将结果集映射成对象
	 * 
	 * @param resultSet
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public <T> T toBean(ResultSet resultSet, Class<T> clazz) throws SQLException;
	
	/**
	 * 插入一个对象
	 * 
	 * @param conn
	 * @param clazz
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public <T> T insert(Connection conn, Class<?> clazz, T bean) throws SQLException;
}
