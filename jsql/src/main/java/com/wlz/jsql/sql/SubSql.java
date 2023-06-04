package com.wlz.jsql.sql;

import java.util.LinkedList;
import java.util.List;

import com.wlz.jsql.sql.builder.ConditionBuilder;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.util.CollectionUtils;

public class SubSql extends SqlFragment{

	private SqlFragment sqlFragment;
	
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		List<Object> paramterList = new LinkedList<>(); 
		paramterList.addAll(sqlFragment.paramters());
		SqlFragment preSqlFragment = pre();
		if(preSqlFragment!=null) {
			List<Object> preParamterList = preSqlFragment.paramters();
			if(CollectionUtils.isNotEmpty(preParamterList)) {
				preParamterList.addAll(paramterList);
				paramterList = preParamterList;
			}
		}
		return paramterList;
	}

	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		
		StringBuffer sql =  new StringBuffer();
		
		sql.append(" ( ");
		sql.append(sqlFragment.toSql());
		sql.append(" ) ");
		return sql.toString();
	}

	public SubSql(SqlFragment sqlFragment) {
		this.sqlFragment = sqlFragment;
	}
	
	

	
	public ConditionBuilder and(Column column) {
		ConditionBuilder builder = new ConditionBuilder();
		And and = new And(column);
		and.setPreSqlFragment(pre());
		builder.setPreSqlFragment(and);
		return builder;
	}

	public ConditionBuilder or(Column column) {
		ConditionBuilder builder = new ConditionBuilder();
		Or or = new Or(column);
		or.setPreSqlFragment(pre());
		builder.setPreSqlFragment(or);
		return builder;
	}
}
