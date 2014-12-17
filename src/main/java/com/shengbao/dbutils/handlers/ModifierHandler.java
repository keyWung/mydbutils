package com.shengbao.dbutils.handlers;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * 修改操作的接口
 * 任何带有修改操作的handler 类必须实现该接口
 * @author shengbao
 * 
 */
public interface ModifierHandler<T> {
	
	/**
	 * 插入一个对象到数据库
	 * @param object 目标对象
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("hiding")
	public <T> T insert(Connection conn, T object) throws SQLException;
}
