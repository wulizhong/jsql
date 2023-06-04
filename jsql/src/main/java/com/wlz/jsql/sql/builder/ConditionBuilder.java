package com.wlz.jsql.sql.builder;

import com.wlz.jsql.sql.*;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.fun.Fun;

public class ConditionBuilder extends SqlFragmentBuilder{
	public ConditionBuilder and(Column field) {
		ConditionBuilder builder = new ConditionBuilder();
		And and = new And(field);
		
		and.setPreSqlFragment(pre());
		builder.setPreSqlFragment(and);
		return builder;
	}
	
	public ConditionBuilder and(RawSql rawSql) {
		ConditionBuilder builder = new ConditionBuilder();
		And and = new And(rawSql);
		
		and.setPreSqlFragment(pre());
		builder.setPreSqlFragment(and);
		return builder;
	}

	public ConditionBuilder or(Column field) {
		ConditionBuilder builder = new ConditionBuilder();
		Or or = new Or(field);
		or.setPreSqlFragment(pre());
		builder.setPreSqlFragment(or);
		return builder;
	}
	
	public ConditionBuilder or(RawSql rawSql) {
		ConditionBuilder builder = new ConditionBuilder();
		Or or = new Or(rawSql);
		or.setPreSqlFragment(pre());
		builder.setPreSqlFragment(or);
		return builder;
	}
	
	public ConditionBuilder and(SubSql subSql) {
		ConditionBuilder builder = new ConditionBuilder();
		And and = new And(subSql);
		and.setPreSqlFragment(pre());
		builder.setPreSqlFragment(and);
		return builder;
	}

	public ConditionBuilder or(SubSql subSql) {
		ConditionBuilder builder = new ConditionBuilder();
		Or or = new Or(subSql);
		or.setPreSqlFragment(pre());
		builder.setPreSqlFragment(or);
		return builder;
	}
	
	public ConditionBuilder and(SqlFragmentBuilder subSqlBuilder) {
		SubSql subSql = new SubSql(subSqlBuilder);
		ConditionBuilder builder = new ConditionBuilder();
		And and = new And(subSql);
		and.setPreSqlFragment(pre());
		builder.setPreSqlFragment(and);
		return builder;
	}

	public ConditionBuilder and(Fun fun) {
		ConditionBuilder builder = new ConditionBuilder();
		And and = new And(fun);
		builder.setPreSqlFragment(and);
		and.setPreSqlFragment(pre());
//		setCurrentSqlFragment(and);
		return builder;
	}
	public ConditionBuilder or(SqlFragmentBuilder subSqlBuilder) {
		SubSql subSql = new SubSql(subSqlBuilder);
		ConditionBuilder builder = new ConditionBuilder();
		Or or = new Or(subSql);
		or.setPreSqlFragment(pre());
		builder.setPreSqlFragment(or);
		return builder;
	}
	public ConditionBuilder or(Fun fun) {
		ConditionBuilder builder = new ConditionBuilder();
		Or or = new Or(fun);
		builder.setPreSqlFragment(or);
		or.setPreSqlFragment(pre());
//		setCurrentSqlFragment(or);
		return builder;
	}
	public GroupByBuilder groupBy(Column ... fields) {
		GroupByBuilder groupByBuilder = new GroupByBuilder();
		GroupBy groupBy = new GroupBy(fields);
		groupBy.setPreSqlFragment(pre());
		groupByBuilder.setPreSqlFragment(groupBy);
		return groupByBuilder;

	}
	
	public OrderByBuilder orderBy(Order ...orders) {
		OrderByBuilder orderByBuilder = new OrderByBuilder();
		OrderBy orderBy = new OrderBy(orders);
		orderBy.setPreSqlFragment(pre());
		orderByBuilder.setPreSqlFragment(orderBy);
		return orderByBuilder;
	}
	
	public Limit limit(int offset,int rows) {
		Limit limit = new Limit(offset,rows);
		limit.setPreSqlFragment(pre());
		return limit;
	}
	
	public Limit limit(int offset) {
		Limit limit = new Limit(offset);
		limit.setPreSqlFragment(pre());
		return limit;
	}
}
