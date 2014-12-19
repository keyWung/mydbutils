package com.shengbao.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * 将多个从数据库查询出来的Bean对象放到ArrayList
 * @author shengbao
 *
 * @param <T>
 */
public class BeanListHandler<T> implements ResultSetHandler<ArrayList<T>>{

	private Class<T> type;
	
	public BeanListHandler(Class<T> type) {
		this.type = type;
	}
	
	/**
	 * 将多个结果的结果集映射成对象放到List集合
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<T> handle(ResultSet rs) throws SQLException {
		ArrayList<T> temp = new ArrayList<T>();
		while (rs.next()) {
			temp.add( (T) rowProcessor.toBean(rs, this.type));
		}
		return temp;
	}
	
}
