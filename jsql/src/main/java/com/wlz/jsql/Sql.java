package com.wlz.jsql;

import java.util.List;

import com.wlz.jsql.sql.database.Table;

public interface Sql {

	public  List<Object> paramters();
	
	public  String toSql(SqlContext sqlContext);
	
	public List<Table> tables();
}
