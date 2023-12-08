package com.wlz.jsql.sql;

import com.wlz.jsql.SqlContext;
import com.wlz.jsql.sql.database.Column;

public class Or extends ConditionSqlFragment{
	public Or(SqlFragment column) {
		sqlFragment = column;
	}

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(" or ");
		sb.append(sqlFragment.toSql(sqlContext));
		sb.append(" ");
		sb.insert(0,pre().toSql(sqlContext));
		return sb.toString();
	}
}
