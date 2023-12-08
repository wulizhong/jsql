package com.wlz.jsql.sql;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.JSqlException;
import com.wlz.jsql.SqlContext;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.util.StringUtils;

public class Set extends SqlFragment{

	private Column[] columns;
	
	private Object[] values;

	public Set(Column[] columns, Object[] values) {
		super();
		this.columns = columns;
		this.values = values;
	}

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		
		if(columns == null||values == null) {
			throw new JSqlException(" fields or values is null");
		}
		
		if(columns.length != values.length) {
			throw new JSqlException(" fields length is not equal values length");
		}
		StringBuffer sb = new StringBuffer(" set ");
		
		List<String> sqlFragments = new ArrayList<>();
		
		for(int i = 0;i<columns.length;i++) {
			sqlFragments.add(columns[i].getName()+" = ? ");
		}
		
		sb.append(StringUtils.join(sqlFragments, ","));
		sb.insert(0, pre().toSql(sqlContext));
		
		return sb.toString();
	}

	@Override
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		List<Object> paramters = new ArrayList<>();
		if(values!=null) {
			for(int i = 0;i<values.length;i++) {
				paramters.add(values[i]);
			}
		}
		return paramters;
	}
	
	
	
	
}
