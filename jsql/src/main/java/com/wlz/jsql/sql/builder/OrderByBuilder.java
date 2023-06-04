package com.wlz.jsql.sql.builder;

import com.wlz.jsql.sql.Limit;

public class OrderByBuilder extends SqlFragmentBuilder{

	public Limit limit(int offset,int rows) {
		Limit limit = new Limit(offset,rows);
		limit.setPreSqlFragment(pre());
		return limit;
	}
	
	public Limit limit(int offset) {
		Limit limit = new Limit(offset);
		limit.setPreSqlFragment(pre());
		return limit;
	}
}
