package com.wlz.jsql.sql;

public class RawSql extends SqlFragment{

	private String rawSql;
	
	public RawSql(String rawSql) {
		this.rawSql = rawSql;
	}

	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		return " "+ rawSql +" ";
	}

}
