package com.shengbao.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.shengbao.dbutils.processor.BasicRowProcessor;
import com.shengbao.dbutils.processor.RowProcessor;

/**
 * 结果集接口
 * @author shengbao
 *
 * @param <T>
 */
public interface ResultSetHandler<T> {
	
	public static final RowProcessor rowProcessor = new BasicRowProcessor();//数据库每一行的处理器
	
	 @SuppressWarnings("hiding")
	<T> T handle(ResultSet rs) throws SQLException;
	 
	 
}
