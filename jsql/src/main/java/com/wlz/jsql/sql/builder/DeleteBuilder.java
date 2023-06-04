package com.wlz.jsql.sql.builder;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.sql.database.Table;

public class DeleteBuilder extends SqlFragmentBuilder{

	private Table table;

	public DeleteBuilder(Table table) {
		super();
		this.table = table;
	}
	
	public WhereBuilder where() {
		WhereBuilder whereBuilder = new WhereBuilder();
		whereBuilder.setPreSqlFragment(this);
		return whereBuilder;
	}

	@Override
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public String toSql() {
		// TODO Auto-generated method stub

		StringBuffer sb = new StringBuffer("delete from ");
		sb.append(table.getTableName());
		sb.append(" ");
		sb.append(table.getTableAlias());
		
		return sb.toString();
	}
	
	
}
