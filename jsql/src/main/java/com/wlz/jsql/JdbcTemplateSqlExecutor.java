//package com.wlz.jsql;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.ResultSetExtractor;
//
//import com.wlz.jsql.sql.database.Table;
//
//public class JdbcTemplateSqlExecutor extends SqlExecutor{
//
//	private JdbcTemplate jdbcTemplate;
//	
//	public JdbcTemplateSqlExecutor(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}	
//	public Record selectList(Sql sql) {
//		
////		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//		
//		
//		String sqlStr = sql.toSql();
//		
//		System.out.println(sqlStr);
//		
//		List<Table> tables = sql.tables();
//		
//		List<Object> paramters = sql.paramters();
//	
//		ResultSetExtractor<Record> resultSetExtractor = new ResultSetExtractor<Record>() {
//			@Override
//			public Record extractData(ResultSet rs) throws SQLException, DataAccessException {
//				// TODO Auto-generated method stub
//				Record record = convertResultSetToRecord(rs,tables);
//				return record;
//			}
//		};
//		Record record = null;
//		if(CollectionUtils.isEmpty(paramters)) {
//			record = jdbcTemplate.query(sqlStr, resultSetExtractor);
//		}else {
//			record = jdbcTemplate.query(sqlStr, resultSetExtractor ,paramters.toArray());
//		}
//		
//		return record;
//	}
//	
//
//	
//	
//}
