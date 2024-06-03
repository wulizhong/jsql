package com.wlz.jsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.sql.InsertResult;
import com.wlz.jsql.sql.database.Table;
import com.wlz.jsql.util.CollectionUtils;

public class SimpleSqlExecutor extends SqlExecutor {

	private Connection connection;

	public SimpleSqlExecutor(Connection connection) {
		this.connection = connection;
	}
	@Override
	public Record selectList(Sql sql) {
		// TODO Auto-generated method stub
		String sqlStr = sql.toSql(getSqlContext());

		System.out.println(sqlStr);

		List<Table> tables = sql.tables();

		List<Object> paramters = sql.paramters();
		try {
			ResultSet resultSet = null;
			if (CollectionUtils.isEmpty(paramters)) {
				Statement statement = connection.createStatement();
				resultSet = statement.executeQuery(sqlStr);
			} else {
				PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
				setValue(preparedStatement, paramters);
				resultSet = preparedStatement.executeQuery();
			}
			Record record = convertResultSetToRecord(resultSet, tables);
			return record;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSqlException(e.getMessage());
		}
	}

	@Override
	public Record selectPage(Sql sql, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		String sqlStr = sql.toSql(getSqlContext());
		System.out.println(sqlStr);

		List<Object> paramters = sql.paramters();

		sqlStr = sqlStr.substring(0, sqlStr.indexOf("select") + 6) + " count(*) "
				+ sqlStr.substring(sqlStr.indexOf("from"));
		System.out.println(sqlStr);

		try {
			ResultSet resultSet = null;
			if (CollectionUtils.isEmpty(paramters)) {
				Statement statement = connection.createStatement();
				resultSet = statement.executeQuery(sqlStr);
			} else {
				PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
				setValue(preparedStatement, paramters);
				resultSet = preparedStatement.executeQuery();
			}

			long total= 0;
			if (resultSet.next()) {
				total  = resultSet.getLong(1);
			}
			Record record;
			if (total > 0) {
				sqlStr = sqlStr + " limit ? , ? ";
				List<Table> tables = sql.tables();
				if (paramters == null) {
					paramters = new ArrayList<>();
				}
				paramters.add(pageNumber, pageSize);
				resultSet = null;
				if (CollectionUtils.isEmpty(paramters)) {
					Statement statement = connection.createStatement();
					resultSet = statement.executeQuery(sqlStr);
				} else {
					PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
					setValue(preparedStatement, paramters);
					resultSet = preparedStatement.executeQuery();
				}
				record = convertResultSetToRecord(resultSet, tables);
			} else {
				record = new Record();
			}
			record.setTotal(total);
			return record;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSqlException(e.getMessage());
		}
	}
	
	

	@Override
	public int selectInt(Sql sql) {
		// TODO Auto-generated method stub
		String sqlStr = sql.toSql(getSqlContext());
		System.out.println(sqlStr);
		List<Object> paramters = sql.paramters();
		try {
			ResultSet resultSet = null;
			if (CollectionUtils.isEmpty(paramters)) {
				Statement statement = connection.createStatement();
				resultSet = statement.executeQuery(sqlStr);
			} else {
				PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
				setValue(preparedStatement, paramters);
				resultSet = preparedStatement.executeQuery();
			}

			int value= 0;
			if (resultSet.next()) {
				value  = resultSet.getInt(1);
			}
			return value;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSqlException(e.getMessage());
		}
	}



	@Override
	public InsertResult insert(Sql sql) {
		// TODO Auto-generated method stub
		String sqlStr = sql.toSql(getSqlContext());
		System.out.println(sqlStr);
		List<Table> tables = sql.tables();
		List<Object> paramters = sql.paramters();
		InsertResult insertResult = new InsertResult();
		try {
			Table table = tables.get(0);
			int rowCount = 0;
			ResultSet resultSet = null;
			if (CollectionUtils.isEmpty(paramters)) {
				Statement statement = connection.createStatement();
				if (table.isGenerateKey()) {
					rowCount = statement.executeUpdate(sqlStr, Statement.RETURN_GENERATED_KEYS);
					resultSet = statement.getGeneratedKeys();
				} else {
					rowCount = statement.executeUpdate(sqlStr);
				}
			} else {
				PreparedStatement preparedStatement;
				if (table.isGenerateKey()) {
					preparedStatement = connection.prepareStatement(sqlStr, Statement.RETURN_GENERATED_KEYS);
				} else {
					preparedStatement = connection.prepareStatement(sqlStr);
				}
				setValue(preparedStatement, paramters);
				rowCount = preparedStatement.executeUpdate();
				if (table.isGenerateKey()) {
					resultSet = preparedStatement.getGeneratedKeys();
				}
			}
			if (table.isGenerateKey() && resultSet != null) {
				ResultSetMetaData rsmd = resultSet.getMetaData();
				if (resultSet.next()) {
					int columnType = rsmd.getColumnType(1);
					Object id = null;
					switch (columnType) {
					case Types.BIGINT:
						id = resultSet.getLong(1);
						break;
					case Types.INTEGER:
						id = resultSet.getInt(1);
					case Types.VARCHAR:
						id = resultSet.getString(1);
						break;

					default:
						break;
					}
					if (id != null) {
						insertResult.setId(id);
					}
				}

			}
			insertResult.setRowCount(rowCount);
			return insertResult;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSqlException(e.getMessage());
		}

	}

	@Override
	public int update(Sql sql) {
		// TODO Auto-generated method stub
		String sqlStr = sql.toSql(getSqlContext());
		System.out.println(sqlStr);
		List<Object> paramters = sql.paramters();
		try {
			if (CollectionUtils.isEmpty(paramters)) {
				Statement statement = connection.createStatement();
				return statement.executeUpdate(sqlStr);
			} else {
				PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
				setValue(preparedStatement, paramters);
				return preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSqlException(e.getMessage());
		}
	}

	@Override
	public int delete(Sql sql) {
		// TODO Auto-generated method stub
		return update(sql);
	}
}
