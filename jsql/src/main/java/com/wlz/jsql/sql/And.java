package com.wlz.jsql.sql;

import com.wlz.jsql.SqlContext;
import com.wlz.jsql.sql.database.Column;

public class And extends ConditionSqlFragment{


	public And(SqlFragment column) {
		sqlFragment = column;
	}
	

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(" and ");
		sb.append(sqlFragment.toSql(sqlContext));
		sb.append(" ");
		sb.insert(0,pre().toSql(sqlContext));
		return sb.toString();
	}

}
