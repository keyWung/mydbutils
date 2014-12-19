package com.shengbao.dbutils.processor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 底层bean处理器
 * 
 * @author shengbao
 *
 */
public class BeanProcessor {
	
	/**
	 * 没有映射的标志
	 */
	public static final int COLUMN_NOT_FIND = -1;
	
	/**
	 * 得到属性描述器数组
	 * @param clazz
	 * @return
	 */
	private PropertyDescriptor[] getProperDiscriptor(Class<?> clazz) {
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] pd = info.getPropertyDescriptors();
		int ii = -1;
		for (int i = 0; i < pd.length; i++) {
			if (pd[i].getName().equals("class")) {
				ii = i;
				break;
			}
		}
		if (ii >= 0) {
			int diff = pd.length - ii -1;
			System.arraycopy(pd, ii + 1, pd, ii, diff);
			pd = Arrays.copyOf(pd, pd.length -1 );
		}
		return pd;
	}
	
	/**
	 * 数据库的一行中的各列映射java bean 属性
	 * @param props
	 * @param rsmd
	 * @return
	 * @throws SQLException
	 */
	private int[] mapColumnsToPropertys(PropertyDescriptor[] props, ResultSetMetaData rsmd) throws SQLException {
		int cols = rsmd.getColumnCount();
		int[] maps = new int[cols];
		
		Arrays.fill(maps, COLUMN_NOT_FIND);
		
		for (int col = 0; col < cols; col++) {
			String colName = rsmd.getColumnName(col+1);
			
			for (int j = 0; j < props.length;j++) {
				if (colName.equalsIgnoreCase(props[j].getName())) {
					maps[col] = j;
				}
			}
		}
		
		return maps;
	}
	
	/**
	 * 映射一个bean
	 * 
	 * @param rs
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public <T> T toBean(ResultSet rs, Class<T> clazz) throws SQLException{
		//拿到类型的属性描述器
		PropertyDescriptor[] props = this.getProperDiscriptor(clazz);
		//拿到结果集的元信息
		ResultSetMetaData rsmd = rs.getMetaData();
		//通过rs元信息映射Bean属性 得到一个映射信息数组
		int[] mapColumnsToPropertys = this.mapColumnsToPropertys(props, rsmd);
		return this.createBean(rs, clazz, props, rsmd, mapColumnsToPropertys);
	}
	
	/**
	 * 创建一个bean
	 * @param rs
	 * @param clazz
	 * @param props
	 * @param rsmd
	 * @param mctp
	 * @return
	 * @throws SQLException
	 */
	private <T> T createBean(ResultSet rs, Class<T> clazz, PropertyDescriptor[] props, ResultSetMetaData rsmd, int[] mctp) throws SQLException {
		T tar = null;
		try {
			tar = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		Object value = null;
		for (int i = 0; i < mctp.length; i++) {
			if (mctp[i] == COLUMN_NOT_FIND) {
				continue;
			}
			
			int index = mctp[i];
			PropertyDescriptor pd = props[index];
			Method setter = pd.getWriteMethod();
			value = this.processColumn(rs, i+1, pd.getPropertyType());
			try {
				setter.invoke(tar, new Object[]{value});
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		return tar;
	}
	
	/**
	 * 将数据库列字段类型转对应java类型
	 * 
	 * @param rs
	 * @param index
	 * @param propType
	 * @return
	 * @throws SQLException
	 */
	protected Object processColumn(ResultSet rs, int index, Class<?> propType) throws SQLException {
		
		if ( !propType.isPrimitive() && rs.getObject(index) == null ) {
		    return null;
		}
		
		if (propType.equals(String.class)) {
		    return rs.getString(index);
		
		} else if (
		    propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
		    return Integer.valueOf(rs.getInt(index));
		
		} else if (
		    propType.equals(Boolean.TYPE) || propType.equals(Boolean.class)) {
		    return Boolean.valueOf(rs.getBoolean(index));
		
		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
		    return Long.valueOf(rs.getLong(index));
		
		} else if (
		    propType.equals(Double.TYPE) || propType.equals(Double.class)) {
		    return Double.valueOf(rs.getDouble(index));
		
		} else if (
		    propType.equals(Float.TYPE) || propType.equals(Float.class)) {
		    return Float.valueOf(rs.getFloat(index));
		
		} else if (
		    propType.equals(Short.TYPE) || propType.equals(Short.class)) {
		    return Short.valueOf(rs.getShort(index));
		
		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
		    return Byte.valueOf(rs.getByte(index));
		
		} else if (propType.equals(Timestamp.class)) {
		    return rs.getTimestamp(index);
		
		} else if (propType.equals(SQLXML.class)) {
		    return rs.getSQLXML(index);
		
		} else {
		    return rs.getObject(index);
		}

   }
	
	/**
	 * 将javabean 插入数据库
	 * @return
	 * @throws SQLException 
	 */
	public <T> T beanToRow(Connection conn, Class<?> clazz, T bean) throws SQLException {
		ResultSet rs = conn.getMetaData().getColumns(null, "%",clazz.getSimpleName().toLowerCase(), "%");
		ArrayList<String> colNames = new ArrayList<String>(10);
		while (rs.next()) {
			 String columnName = rs.getString("COLUMN_NAME");
			 System.out.println("cname : " + columnName);
			 colNames.add(columnName);
		}
		
		System.out.println(colNames.size());
		
		//拿到类型的属性描述器
		PropertyDescriptor[] props = this.getProperDiscriptor(clazz);
		PreparedStatement ps = conn.prepareStatement("insert into user(userid, age, name) values(?, ?, ?)");
		
		for (int i = 0; i < props.length; i++) {
			
			PropertyDescriptor descriptor = props[i];
			if (descriptor.getName().equals("class")) {
				continue;
			}
			
			System.out.println(descriptor.getName());
			if (colNames.indexOf(descriptor.getName()) == -1) {
				continue;
			}
			int index =  colNames.indexOf(descriptor.getName()) + 1;
			Method method = descriptor.getReadMethod();
			
			Object value = null;
			try {
				value = method.invoke(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (value != null) {
				ps.setObject(index, value);
				value = null;
			}
		}
		ps.execute();
		
		return null;
	}
	
}
