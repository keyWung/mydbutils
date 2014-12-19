package com.shengbao.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * MapHandler 用于将单个结果集映射成一个HashMap
 * 形如：Map<typeName,value>
 * @author shengbao
 *
 * @param <T>
 */
public class MapHanlder<T> implements ResultSetHandler<T>{

	@SuppressWarnings("hiding")
	public <T> T handle(ResultSet rs) throws SQLException {
		
		return null;
	}

}
