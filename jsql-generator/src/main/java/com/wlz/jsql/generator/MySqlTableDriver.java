package com.wlz.jsql.generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlTableDriver extends TableDriver {

	@Override
	public Table getTable(Connection connection, String tableName) {
		// TODO Auto-generated method stub
		Table table = new Table();
		table.setName(tableName);
		List<Column> columns = getColumn(connection,tableName);
		table.setColumns(columns);
		
		for(Column c : columns) {
			if(c.isPrimaryKey()&&c.isAutoIncrement()) {
				table.setAutoIncrement(true);
				break;
			}
		}
		return table;
	}

	
	private List<Column> getColumn(Connection connection, String tableName){
		String databaseName = null;
		try {
			databaseName = connection.getCatalog();
//			connection.getSchema();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		List<Column> columns = new ArrayList<>();
		String sql = "select * "
				+ "from\r\n"
				+ "	information_schema.columns\r\n"
				+ "where\r\n"
				+ "	table_name = '"+tableName+"'"
				+ " and table_schema ='"+databaseName+"'";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Column c = new Column();
				columns.add(c);
				c.setName(resultSet.getString("COLUMN_NAME"));
				String type = resultSet.getString("DATA_TYPE");
				if(type.equalsIgnoreCase("bigint")) {
					c.setType(Types.BIGINT);
				}else if(type.equalsIgnoreCase("decimal")) {
					c.setType(Types.DECIMAL);
				}else if(type.equalsIgnoreCase("double")) {
					c.setType(Types.DOUBLE);
				}else if(type.equalsIgnoreCase("varchar")) {
					c.setType(Types.VARCHAR);
				}else if(type.equalsIgnoreCase("int")) {
					c.setType(Types.INTEGER);
				}else if(type.equalsIgnoreCase("float")) {
					c.setType(Types.FLOAT);
				}else if(type.equalsIgnoreCase("tinyint")) {
					c.setType(Types.TINYINT);
				}else if(type.equalsIgnoreCase("text")||type.equalsIgnoreCase("mediumtext")||type.equalsIgnoreCase("longtext")) {
					c.setType(Types.VARCHAR);
				}else if(type.equalsIgnoreCase("timestamp")) {
					c.setType(Types.TIMESTAMP);
				}else if(type.equalsIgnoreCase("datetime")) {
					c.setType(Types.DATE);
				}
				String comment = resultSet.getString("COLUMN_COMMENT");
				String primityKey = resultSet.getString("COLUMN_KEY");
				c.setComment(comment);
				c.setPrimaryKey("pri".equalsIgnoreCase(primityKey));
				if(c.isPrimaryKey()) {
					
					String extra = resultSet.getString("extra");
					
					if(extra!=null&&extra.contains("auto_increment")) {
						c.setAutoIncrement(true);
					}
					
					
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return columns;
	}
	

	public List<String> getAllTableName(Connection connection){
		String sql = "show tables;";
		List<String> tableNameList = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				tableNameList.add(resultSet.getString(1));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return tableNameList;
		
	}

}
