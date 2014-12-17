package com.shengbao.dbutils.handlers;

import java.sql.SQLException;

public interface ModifierHandler<T> {
	
	@SuppressWarnings("hiding")
	public <T> T insert(T object) throws SQLException;
}
