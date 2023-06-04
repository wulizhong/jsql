package com.wlz.jsql;

public class SqlContext {

	private static ThreadLocal<SqlDialect> sqlDialectThreadLocal = new ThreadLocal<>();
	
	public static void setSqlDialect(SqlDialect sqlDialect) {
		sqlDialectThreadLocal.set(sqlDialect);
	}

	public static SqlDialect getSqlDialect() {
		return sqlDialectThreadLocal.get();
	}
	
	public static void clear() {
		sqlDialectThreadLocal.remove();
	}
}
