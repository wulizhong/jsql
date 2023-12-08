package com.wlz.jsql.sql;

import com.wlz.jsql.SqlContext;

public class RawSql extends SqlFragment{

	private String rawSql;
	
	public RawSql(String rawSql) {
		this.rawSql = rawSql;
	}

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		return " "+ rawSql +" ";
	}

}
