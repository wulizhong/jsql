package com.wlz.jsql.sql;

import java.util.Arrays;
import java.util.List;

import com.wlz.jsql.JSqlException;
import com.wlz.jsql.SqlContext;
import com.wlz.jsql.sql.database.Table;

public class From extends SqlFragment{

	private Table[] tables;
	
	public From(Table... tables) {
		this.tables = tables;
	}
	
	@Override
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		return pre().paramters();
	}

	
	
	@Override
	public List<Table> tables() {
		// TODO Auto-generated method stub
		return Arrays.asList(tables);
	}

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		
		if(tables == null||tables.length == 0) {
			throw new JSqlException("Table must not be null");
		}
		
		StringBuffer sb = new StringBuffer(" from ");
		for(int i = 0;i<tables.length;i++) {
			Table t = tables[i];
			sb.append(t.getTableName());
			sb.append(" ");
			sb.append(t.getTableAlias());
			if(i!=tables.length-1) {
				sb.append(", ");
			}
		}
		sb.insert(0, pre().toSql(sqlContext));
		return sb.toString();
	}

}
