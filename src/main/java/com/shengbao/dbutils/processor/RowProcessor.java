package com.shengbao.dbutils.processor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowProcessor {
	
	public <T> T toBean(ResultSet resultSet, Class<T> clazz) throws SQLException;
}
