package com.wlz.jsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.wlz.jsql.springboot.starter.JSqlProperty;
import com.wlz.jsql.sql.InsertResult;
import com.wlz.jsql.sql.database.Table;
import com.wlz.jsql.util.CollectionUtils;
import com.wlz.jsql.util.StringUtils;

public class JdbcTemplateSqlExecutor extends SqlExecutor {

	private JdbcTemplate jdbcTemplate;

//	private boolean isDebug = true;
	
	private JSqlProperty jsqlProperty;

	public JdbcTemplateSqlExecutor(JdbcTemplate jdbcTemplate,JSqlProperty jsqlProperty) {
		this.jsqlProperty = jsqlProperty;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int selectInt(Sql sql) {
		// TODO Auto-generated method stub
		String sqlStr = sql.toSql();
		List<Object> paramters = sql.paramters();
		if (jsqlProperty.isSqlPrint()||jsqlProperty.isRawSqlPrint()||jsqlProperty.isParamPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logSql(stackTrace, sqlStr, paramters);
		}
		ResultSetExtractor<Integer> resultSetExtractor = new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				int value= 0;
				if (rs.next()) {
					value  = rs.getInt(1);
				}
				return value;
			}
		};
		int result = 0;
		if (CollectionUtils.isEmpty(paramters)) {
			result = jdbcTemplate.query(sqlStr, resultSetExtractor);
		}else {
			result = jdbcTemplate.query(sqlStr, resultSetExtractor, paramters.toArray());
		}
		if (jsqlProperty.isResultCountPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logResult(stackTrace, result);
		}
		return result;
	}

	public Record selectList(Sql sql) {
		String sqlStr = sql.toSql();
		List<Table> tables = sql.tables();
		List<Object> paramters = sql.paramters();
		if (jsqlProperty.isSqlPrint()||jsqlProperty.isRawSqlPrint()||jsqlProperty.isParamPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logSql(stackTrace, sqlStr, paramters);
		}
		ResultSetExtractor<Record> resultSetExtractor = new ResultSetExtractor<Record>() {
			@Override
			public Record extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				Record record = convertResultSetToRecord(rs, tables);
				return record;
			}
		};
		Record record = null;
		if (CollectionUtils.isEmpty(paramters)) {
			record = jdbcTemplate.query(sqlStr, resultSetExtractor);
		} else {
			record = jdbcTemplate.query(sqlStr, resultSetExtractor, paramters.toArray());
		}
		if (jsqlProperty.isResultCountPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logResult(stackTrace, record.getDbResultMap());
		}

		return record;
	}
	
	@Override
	public Record selectPage(Sql sql, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		String pageSql = sql.toSql();

		

		List<Object> paramters = sql.paramters();

		String sqlStr =  pageSql.substring(0, pageSql.indexOf("select") + 6) + " count(*) "
				+ pageSql.substring(pageSql.indexOf("from"));
		if (jsqlProperty.isSqlPrint()||jsqlProperty.isRawSqlPrint()||jsqlProperty.isParamPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logSql(stackTrace, sqlStr, paramters);
		}

		ResultSetExtractor<Integer> resultSetExtractor = new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				int value= 0;
				if (rs.next()) {
					value  = rs.getInt(1);
				}
				return value;
			}
		};
		long total = 0;
		if (CollectionUtils.isEmpty(paramters)) {
			total = jdbcTemplate.query(sqlStr, resultSetExtractor);
		}else {
			total = jdbcTemplate.query(sqlStr, resultSetExtractor, paramters.toArray());
		}
		if (jsqlProperty.isResultCountPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logResult(stackTrace, (int)total);
		}
		
		Record record;
		if (total > 0) {
			pageSql = pageSql + " limit ? , ? ";
			List<Table> tables = sql.tables();
			if (paramters == null) {
				paramters = new ArrayList<>();
			}
			paramters.add(pageNumber);
			paramters.add(pageSize);
			if (jsqlProperty.isSqlPrint()||jsqlProperty.isRawSqlPrint()||jsqlProperty.isParamPrint()) {
				StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				logSql(stackTrace, pageSql, paramters);
			}
			ResultSetExtractor<Record> recordResultSetExtractor = new ResultSetExtractor<Record>() {
				@Override
				public Record extractData(ResultSet rs) throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					Record record = convertResultSetToRecord(rs, tables);
					return record;
				}
			};
			if (CollectionUtils.isEmpty(paramters)) {
				record = jdbcTemplate.query(pageSql, recordResultSetExtractor);
			} else {
				record = jdbcTemplate.query(pageSql, recordResultSetExtractor, paramters.toArray());
			}
			if (jsqlProperty.isResultCountPrint()) {
				StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				logResult(stackTrace, record.getDbResultMap());
			}
		} else {
			record = new Record();
		}
		record.setTotal(total);
		
		return record;
	}
	

	@Override
	public InsertResult insert(Sql sql) {
		// TODO Auto-generated method stub
		String sqlStr = sql.toSql();
		List<Table> tables = sql.tables();
		List<Object> paramters = sql.paramters();
		if (jsqlProperty.isSqlPrint()||jsqlProperty.isRawSqlPrint()||jsqlProperty.isParamPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logSql(stackTrace, sqlStr, paramters);
		}
		InsertResult insertResult = new InsertResult();
		Table table = tables.get(0);
		int rowCount = 0;
		Number key = null;
		if (CollectionUtils.isEmpty(paramters)) {
			if (table.isGenerateKey()) {
				KeyHolder keyHolder = new GeneratedKeyHolder();
				rowCount = jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement ps = con.prepareStatement(sqlStr, PreparedStatement.RETURN_GENERATED_KEYS);
						return ps;
					}
				}, keyHolder);
				key = keyHolder.getKey();
			} else {
				rowCount = jdbcTemplate.update(sqlStr);
			}
		} else {
			if (table.isGenerateKey()) {
				KeyHolder keyHolder = new GeneratedKeyHolder();
				rowCount = jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement ps = con.prepareStatement(sqlStr, PreparedStatement.RETURN_GENERATED_KEYS);
						setValue(ps, paramters);
						return ps;
					}
				}, keyHolder);
				key = keyHolder.getKey();
			} else {
				rowCount = jdbcTemplate.update(sqlStr);
			}
		}
		if (jsqlProperty.isResultCountPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logResult(stackTrace, rowCount);
		}
		if (table.isGenerateKey() && key != null) {
			insertResult.setId(key);
		}
		insertResult.setRowCount(rowCount);
		return insertResult;
	}

	@Override
	public int update(Sql sql) {
		// TODO Auto-generated method stub
		String sqlStr = sql.toSql();
		List<Object> paramters = sql.paramters();
		if (jsqlProperty.isSqlPrint()||jsqlProperty.isRawSqlPrint()||jsqlProperty.isParamPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logSql(stackTrace, sqlStr, paramters);
		}
		int count = 0;
		if (CollectionUtils.isEmpty(paramters)) {
			count = jdbcTemplate.update(sqlStr);
		} else {
			count = jdbcTemplate.update(sqlStr, paramters.toArray());
		}
		if (jsqlProperty.isResultCountPrint()) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			logResult(stackTrace, count);
		}
		return count;
	}

	@Override
	public int delete(Sql sql) {
		// TODO Auto-generated method stub
		return update(sql);
	}

	private void logSql(StackTraceElement[] stackTrace, String sql, List<Object> paramters) {
		StackTraceElement st = getInvokeMethod(stackTrace);
		String startString = getLogStartString(st);
		Logger logger = LoggerFactory.getLogger(JdbcTemplateSqlExecutor.class);
		if(jsqlProperty.isSqlPrint()) {
			logger.debug(startString + ":sql    >== " + sql);
		}
		
		if (CollectionUtils.isNotEmpty(paramters)) {
			if(jsqlProperty.isParamPrint()) {
				logger.debug(startString + ":params >== " + StringUtils.join(paramters, ","));
			}
			
			String rawSql = new String(sql);
			for (Object obj : paramters) {
				int p = rawSql.indexOf("?");
				String v = null;
				if (obj instanceof Number) {
					v = obj.toString();
				} else {
					v = "'" + obj.toString() + "'";
				}
				rawSql = rawSql.substring(0, p) + v + rawSql.substring(p + 1);
			}
			if(jsqlProperty.isRawSqlPrint()) {
				logger.debug(startString + ":rawsql >== " + rawSql);
			}
			
		}
	}

	@SuppressWarnings("rawtypes")
	private void logResult(StackTraceElement[] stackTrace, List list) {
		logResult(stackTrace, CollectionUtils.isEmpty(list) ? 0 : list.size());
	}

	private void logResult(StackTraceElement[] stackTrace, int size) {
		StackTraceElement st = getInvokeMethod(stackTrace);
		String startString = getLogStartString(st);
		Logger logger = LoggerFactory.getLogger(JdbcTemplateSqlExecutor.class);
		logger.debug(startString + ":count  <== " + size);
	}

	private String getLogStartString(StackTraceElement st) {
		String className = st.getClassName();
		className = className.substring(className.lastIndexOf(".")+1);
		return className + "." + st.getMethodName() + " " + st.getLineNumber() + ":";
	}

	private StackTraceElement getInvokeMethod(StackTraceElement[] stackTrace) {
		int index = 0;
		for (int i = 0; i < stackTrace.length; i++) {
			StackTraceElement st = stackTrace[i];
			if (st.getClassName().equals(getClass().getName())) {
				index = i;
				break;
			}
		}
		return stackTrace[index + 1];
	}

}
