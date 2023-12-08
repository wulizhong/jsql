package com.wlz.jsql.sql;

import com.wlz.jsql.SqlContext;

public class Having extends ConditionSqlFragment{


	
	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(" having ");
		sb.append(sqlFragment.toSql(sqlContext));
		sb.append(" ");
		sb.insert(0,pre().toSql(sqlContext));
		return sb.toString();
	}

	public Having(SqlFragment field) {
		this.sqlFragment = field;
	}
	
//	public Having(SubSql subSql) {
//		this.sqlFragment = subSql;
//	}
}
