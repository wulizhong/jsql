package com.wlz.jsql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wlz.jsql.sql.InsertResult;
import com.wlz.jsql.sql.database.Table;
import com.wlz.jsql.util.CollectionUtils;
import com.wlz.jsql.util.FieldNameUtils;
import com.wlz.jsql.util.ReflectionUtils;

public class SqlExecutor {

	protected SqlContext sqlContext;

	public InsertResult insert(Sql sql) {
		return null;
	}
	
	public int delete(Sql sql) {
		return 0;
	}
	
	public int update(Sql sql) {
		return 0;
	}
	
	public <T> T selectOne(Sql sql, Class<T> clazz) {
		List<T> list = selectList(sql,clazz);
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	public int selectInt(Sql sql) {
		return 0;
	}
	
	public <T> List<T> selectList(Sql sql, Class<T> clazz) {
		Record record = selectList(sql);
		return record.getList(clazz);
	}

	public Record selectPage(Sql sql,int pageNumber,int pageSize) {
		return null;
	}
	
	public Record selectList(Sql sql) {
		return null;
	}

	protected Record convertResultSetToRecord(ResultSet rs, List<Table> tables) {
		Record record = new Record();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			Map<Class<?>, List<Object>> resultListMap = new HashMap<>();
			while (rs.next()) {
				Map<String,Object> resultMap = new HashMap<>();
				Map<String,Object> dbResultMap = new HashMap<>();
				record.getResultMap().add(resultMap);
				record.getDbResultMap().add(dbResultMap);

				Map<Class<?>, Object> objectMap = new HashMap<Class<?>, Object>();
				for (int i = 1; i <= count; i++) {
					Table table = null;
					for (Table t : tables) {
						if (t.getTableName().toLowerCase().equals(metaData.getTableName(i))) {
							table = t;
						}
					}
					String columnName = metaData.getColumnName(i);
					String javaFieldName = FieldNameUtils.underlineToHump(columnName,true);
					if(table == null) {
						for (Table t : tables) {
							Class<?> clazz = t.getEntityType();
							Field field = ReflectionUtils.findField(clazz, javaFieldName);
							if(field!=null) {
								table = t;
								break;
							}
						}
					}
					Object value = null;
					if(table!=null) {
						Object result = objectMap.get(table.getEntityType());
						if (result == null) {
							try {
								result = table.getEntityType().newInstance();
								
								List<Object> list = resultListMap.get(table.getEntityType());
								if (list == null) {
									list = new ArrayList<>();
									resultListMap.put(table.getEntityType(), list);
									record.put(table.getEntityType(), list);
								}
								list.add(result);
							} catch (InstantiationException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							objectMap.put(table.getEntityType(), result);
						}
						String setMethodName = FieldNameUtils.convertToSetMethodName(metaData.getColumnName(i), true);
						Method setMethod = ReflectionUtils.findSetMethod(table.getEntityType(), setMethodName);
						if (setMethod == null) {
							continue;
						}
						Class<?>[] parameterTypes = setMethod.getParameterTypes();
						if (parameterTypes == null || parameterTypes.length == 0) {
							throw new JSqlException(setMethod.getName() + " must have parameter");
						}
						Class<?> parameterType = parameterTypes[0];
						value = getValue(rs, parameterType, i);
						setMethod.invoke(result, value);
					}
					if(value == null) {
						value = rs.getObject(i);
					}
					resultMap.put(javaFieldName, value);
					dbResultMap.put(columnName, value);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

	private Object getValue(ResultSet rs, Class<?> parameterType, int index) throws SQLException {
		Object value = null;
		if (parameterType == Long.class) {
			value = rs.getLong(index);
		} else if (parameterType == Integer.class) {
			value = rs.getInt(index);
		} else if (parameterType == BigDecimal.class) {
			value = rs.getBigDecimal(index);
		} else if (parameterType == String.class) {
			value = rs.getString(index);
		} else if (parameterType == Double.class) {
			value = rs.getDouble(index);
		} else if (parameterType == Float.class) {
			value = rs.getFloat(index);
		} else if (parameterType == Date.class) {
			value = rs.getTimestamp(index);
		} else if (parameterType == Short.class) {
			value = rs.getShort(index);
		} else if (parameterType == Byte.class) {
			value = rs.getByte(index);
		}else {
			value = rs.getObject(index);
		}
		return value;
	}
	
	protected void setValue(PreparedStatement preparedStatement, List<Object> paramters) throws SQLException {

		for (int i = 0; i < paramters.size(); i++) {
			Object param = paramters.get(i);
			int index = i + 1;
			if (param instanceof Integer) {
				preparedStatement.setInt(index, (int) param);
			} else if (param instanceof String) {
				preparedStatement.setString(index, (String) param);
			} else if (param instanceof Long) {
				preparedStatement.setLong(index, (long) param);
			} else if (param instanceof Short) {
				preparedStatement.setShort(index, (short) param);
			} else if (param instanceof Byte) {
				preparedStatement.setByte(index, (byte) param);
			} else if (param instanceof Float) {
				preparedStatement.setFloat(index, (float) param);
			} else if (param instanceof Double) {
				preparedStatement.setDouble(index, (double) param);
			} else if (param instanceof BigDecimal) {
				preparedStatement.setBigDecimal(index, (BigDecimal) param);
			} else if (param instanceof Date) {
				preparedStatement.setDate(index, new java.sql.Date(((Date) param).getTime()));
			} else {
				preparedStatement.setObject(index, param);
			}
		}
	}

	public SqlContext getSqlContext() {
		return sqlContext;
	}

	public void setSqlContext(SqlContext sqlContext) {
		this.sqlContext = sqlContext;
	}
}
