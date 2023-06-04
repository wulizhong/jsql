package com.wlz.jsql.sql;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.util.StringUtils;

public class GroupBy extends SqlFragment{

	private Column[] columns;
	
	public GroupBy(Column ...columns) {
		this.columns = columns;
	}


	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		if(columns == null) {
			return pre().toSql();
		}
		StringBuffer sb = new StringBuffer(" group by ");
		
		List<String> sqlFragmentList = new ArrayList<>();
		
		for(Column f : columns) {
			sqlFragmentList.add(f.toSql());
		}
		sb.append(StringUtils.join(sqlFragmentList, ","));
		sb.append(" ");
		sb.insert(0, pre().toSql());
		return sb.toString();
	}
	
}
