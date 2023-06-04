package com.wlz.jsql;

import com.wlz.jsql.sql.SqlFragment;
import com.wlz.jsql.sql.builder.DslBuilder;
import com.wlz.jsql.sql.builder.InsertBuilder;
import com.wlz.jsql.sql.builder.SelectBuilder;
import com.wlz.jsql.sql.builder.UpdateBuilder;
import com.wlz.jsql.sql.builder.WhereBuilder;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.database.Table;

public  class DSL {
	
	public static SelectBuilder select() {
		DslBuilder dslBuilder = new DslBuilder();
		return dslBuilder.select();
	}
	
	public static SelectBuilder select(SqlFragment... fields) {
		DslBuilder dslBuilder = new DslBuilder();
		return dslBuilder.select(fields);
	}
	
	public static InsertBuilder insertInto(Table table,Column... fields) {
		DslBuilder dslBuilder = new DslBuilder();
		return dslBuilder.insertInto(table,fields);
	}
	
	public static UpdateBuilder update(Table table) {
		DslBuilder dslBuilder = new DslBuilder();
		return dslBuilder.update(table);
	}
	
	public static WhereBuilder deleteFrom(Table table) {
		DslBuilder dslBuilder = new DslBuilder();
		return dslBuilder.deleteFrom(table);
	}
}
