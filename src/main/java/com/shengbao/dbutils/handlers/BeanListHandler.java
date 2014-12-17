package com.shengbao.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BeanListHandler<T> implements ResultSetHandler<ArrayList<T>>{

	private Class<T> type;
	
	public BeanListHandler(Class<T> type) {
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<T> handle(ResultSet rs) throws SQLException {
		ArrayList<T> temp = new ArrayList<T>();
		while (rs.next()) {
			temp.add( (T) rowProcessor.toBean(rs, this.type));
		}
		return temp;
	}
	
}
