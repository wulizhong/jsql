package com.wlz.jsql.sql;

import com.wlz.jsql.SqlContext;
import com.wlz.jsql.sql.database.Column;

public class Where extends ConditionSqlFragment{

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(" where ");
		sb.append(sqlFragment.toSql(sqlContext));
		sb.append(" ");
		sb.insert(0,pre().toSql(sqlContext));
		return sb.toString();
	}

	public RawSql rawSql;
	
	public Where(SqlFragment rawSql) {
		this.sqlFragment = rawSql;
	}
	
//	public Where(Column column) {
//		this.sqlFragment = column;
//	}
//
//	public Where(SubSql subSql) {
//		this.sqlFragment = subSql;
//	}
}
