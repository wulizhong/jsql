package com.wlz.jsql.sql.builder;

import com.wlz.jsql.SqlContext;
import com.wlz.jsql.sql.Set;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.database.Table;

public class UpdateBuilder extends SqlFragmentBuilder{
	private Table table;
	

	public UpdateBuilder(Table table) {
		super();
		this.table = table;
	}

	public WhereBuilder set(Column[] fields,Object[] values) {
		WhereBuilder whereBuilder = new WhereBuilder();
		Set set = new Set(fields,values);
		set.setPreSqlFragment(this);
		whereBuilder.setPreSqlFragment(set);
		return whereBuilder;
	}
	public WhereBuilder set(Column field,Object value) {
		return set(new Column[] {field},new Object[] {value});
	}

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("update ");
		sb.append(table.getTableName());
		sb.append(" ");
		sb.append(table.getTableAlias());
		return sb.toString();
	}
	
	
}
