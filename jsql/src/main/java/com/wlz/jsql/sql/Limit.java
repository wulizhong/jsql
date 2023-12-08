package com.wlz.jsql.sql;

import com.wlz.jsql.SqlContext;

public class Limit extends SqlFragment{

	private int offset = -1;
	
	private int rows = -1;

	public Limit(int offset, int rows) {
		super();
		this.offset = offset;
		this.rows = rows;
	}

	public Limit(int offset) {
		super();
		this.offset = offset;
	}

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(" limit ");
		sb.append(offset);
		if(rows>0) {
			sb.append(" , ");
			sb.append(rows);
			sb.append(" ");
		}
		sb.insert(0, pre().toSql(sqlContext));
		return sb.toString();
	}
	
}
