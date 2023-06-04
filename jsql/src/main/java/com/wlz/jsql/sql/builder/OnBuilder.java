package com.wlz.jsql.sql.builder;

import com.wlz.jsql.sql.On;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.database.Table;

public class OnBuilder extends SqlFragmentBuilder{

	private Table table;
	
	private String joinOnType;
	
	public OnBuilder(Table table,String joinOnType) {
		super();
		this.table = table;
		this.joinOnType = joinOnType;
	}

	public FromBuilder on(Column field) {
		FromBuilder joinBuilder = new FromBuilder();
		On on = new On(table,joinOnType,field);
		on.setPreSqlFragment(pre());
		joinBuilder.setPreSqlFragment(on);
		return joinBuilder;
	}
}
