package com.wlz.jsql.sql.builder;

import com.wlz.jsql.sql.RawSql;
import com.wlz.jsql.sql.SubSql;
import com.wlz.jsql.sql.Where;
import com.wlz.jsql.sql.database.Column;

public class WhereBuilder extends SqlFragmentBuilder{

	public ConditionBuilder where(Column field) {
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(field);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());
		return builder;
	}
	
	public ConditionBuilder where(SubSql subSql) {
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(subSql);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());
		return builder;
	}
	
	public ConditionBuilder where(RawSql rawSql) {
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(rawSql);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());

		return builder;
	}
	
	public ConditionBuilder where(SqlFragmentBuilder sqlBuilder) {
		SubSql subSql = new SubSql(sqlBuilder);
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(subSql);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());
		return builder;
	}
	
}
