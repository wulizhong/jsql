package com.wlz.jsql.sql.builder;

import com.wlz.jsql.sql.From;
import com.wlz.jsql.sql.database.Table;

public class SelectBuilder extends SqlFragmentBuilder{
	
	public <T extends Table> FromBuilder  from(Table... tables) {
		From from = new From(tables);
		FromBuilder whereBuilder = new FromBuilder();
		whereBuilder.setPreSqlFragment(from);
		from.setPreSqlFragment(pre());
//		setCurrentSqlFragment(from);
		return whereBuilder;
	}

	
	
}
