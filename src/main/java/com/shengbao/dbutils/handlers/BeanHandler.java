package com.shengbao.dbutils.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shengbao.dbutils.processor.BasicRowProcessor;
import com.shengbao.dbutils.processor.RowProcessor;

/**
 * Bean处理类 暂时用于转发
 * @author shengbao
 *
 * @param <T>
 */
public class BeanHandler<T> implements ResultSetHandler<T>, ModifierHandler<T> {
	private Class<T> type;//字节码类型
	
	static final RowProcessor rowProcessor = new BasicRowProcessor();//数据库每一行的处理器
	
	public BeanHandler(Class<T> type) {
		this.setType(type);
	}

	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T handle(ResultSet rs) throws SQLException {
		return rs.next() ? (T) rowProcessor.toBean(rs, this.type) : null;
	}
	
	/**
	 * 插入一个对象，不带参数
	 * @param bean 插入数据库的目标javabean 对象
	 */
	@SuppressWarnings("hiding")
	public <T> T insert(Connection conn, T bean) throws SQLException {
		return rowProcessor.insert(conn,this.type,bean);
	}
	
}
