package com.wlz.jsql.sql;

import com.wlz.jsql.sql.database.Column;

public class Where extends ConditionSqlFragment{

	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(" where ");
		sb.append(sqlFragment.toSql());
		sb.append(" ");
		sb.insert(0,pre().toSql());
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
