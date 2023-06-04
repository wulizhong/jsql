package com.wlz.jsql.sql.builder;

import com.wlz.jsql.sql.Select;
import com.wlz.jsql.sql.SqlFragment;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.database.Table;

public class DslBuilder extends SqlFragmentBuilder{

	SqlFragment currentSqlFragment;
	
	public SelectBuilder select() {
		SelectBuilder fromBuilder = new SelectBuilder();
		Select select = new Select();
		fromBuilder.setPreSqlFragment(select);
		this.currentSqlFragment = select;
		return fromBuilder;
	}
	
	public SelectBuilder select(SqlFragment... fields) {
		SelectBuilder fromBuilder = new SelectBuilder();
		Select select = new Select(fields);
		fromBuilder.setPreSqlFragment(select);
		this.currentSqlFragment = select;
		return fromBuilder;
	}
	
	public InsertBuilder insertInto(Table table) {
		InsertBuilder insertBuilder = new InsertBuilder(table);
		return insertBuilder;
	}
	public InsertBuilder insertInto(Table table,Column ...fields) {
		InsertBuilder insertBuilder = new InsertBuilder(table,fields);
		return insertBuilder;
	}
	
	public UpdateBuilder update(Table table) {
		UpdateBuilder updateBuilder = new UpdateBuilder(table);
		return updateBuilder;
	}
	
	public WhereBuilder deleteFrom(Table table) {
		DeleteBuilder deleteBuilder = new DeleteBuilder(table);
		return deleteBuilder.where();
	}

	@Override
	protected SqlFragment currentSqlFragment() {
		// TODO Auto-generated method stub
		return currentSqlFragment;
	}
	
	
}
