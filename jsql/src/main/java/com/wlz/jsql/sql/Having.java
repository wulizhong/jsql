package com.wlz.jsql.sql;

public class Having extends ConditionSqlFragment{


	
	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(" having ");
		sb.append(sqlFragment.toSql());
		sb.append(" ");
		sb.insert(0,pre().toSql());
		return sb.toString();
	}

	public Having(SqlFragment field) {
		this.sqlFragment = field;
	}
	
//	public Having(SubSql subSql) {
//		this.sqlFragment = subSql;
//	}
}
