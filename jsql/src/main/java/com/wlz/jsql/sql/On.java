package com.wlz.jsql.sql;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.database.Table;
import com.wlz.jsql.util.CollectionUtils;

public class On extends SqlFragment{

	private Column column;
	
	private Table table;
	
	private String joinOnType;

	public On(Table table,String joinOnType,Column column) {
		this.table = table;
		this.joinOnType = joinOnType;
		this.column = column;
	}

	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(" ");
		sb.append(joinOnType);
		sb.append(" ");
		sb.append(table.getTableName());
		sb.append(" ");
		
		if(table.getTableAlias()!=null&&table.getTableAlias().length()>0) {
			sb.append(table.getTableAlias());
			sb.append(" ");
		}
		if(column!=null) {
			sb.append(" on ");
			sb.append(column.toSql());
		}
		
		sb.append(" ");
		sb.insert(0,pre().toSql());
		return sb.toString();
	}

	@Override
	public List<Table> tables() {
		// TODO Auto-generated method stub
		ArrayList<Table> tables = new ArrayList<>();
		List<Table> preTables = pre().tables();
		if(preTables!=null&&!preTables.isEmpty()) {
			tables.addAll(preTables);
		}
		tables.add(table);
		return tables;
	}

	@Override
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		List<Object> paramters = new ArrayList<>();
		List<Object> preParamters = pre().paramters();
		if(preParamters!=null&&!preParamters.isEmpty()) {
			paramters.add(paramters);
		}
		if(column!=null&&CollectionUtils.isNotEmpty(column.paramters())) {
			paramters.add(column.paramters());
		}
		return paramters;
	}
	
	

	
}
