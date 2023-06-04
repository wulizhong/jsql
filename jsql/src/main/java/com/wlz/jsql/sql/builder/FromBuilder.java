package com.wlz.jsql.sql.builder;

import com.wlz.jsql.sql.GroupBy;
import com.wlz.jsql.sql.Limit;
import com.wlz.jsql.sql.Order;
import com.wlz.jsql.sql.OrderBy;
import com.wlz.jsql.sql.RawSql;
import com.wlz.jsql.sql.SubSql;
import com.wlz.jsql.sql.Where;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.database.Table;
import com.wlz.jsql.sql.fun.Fun;
import com.wlz.jsql.sql.fun.Fun1;
import com.wlz.jsql.sql.fun.Fun2;

public class FromBuilder extends SqlFragmentBuilder{

	public OnBuilder join(Table table) {
		return buildOnBuilder(table,"join");
	}
	
	public OnBuilder innerJoin(Table table) {
		return buildOnBuilder(table,"inner join");
	}
	
	public OnBuilder leftJoin(Table table) {
		return buildOnBuilder(table,"left join");
	}
	
	public OnBuilder rightJoin(Table table) {
		return buildOnBuilder(table,"right join");
	}
	

	
	public ConditionBuilder where(Column field) {
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(field);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());
//		setCurrentSqlFragment(where);
		return builder;
	}
	public ConditionBuilder where(Fun fun) {
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(fun);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());
//		setCurrentSqlFragment(where);
		return builder;
	}

	
	public ConditionBuilder where(SubSql subSql) {
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(subSql);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());
//		setCurrentSqlFragment(where);
		return builder;
	}
	
	public ConditionBuilder where(SqlFragmentBuilder sqlBuilder) {
		SubSql subSql = new SubSql(sqlBuilder);
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(subSql);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());
//		setCurrentSqlFragment(where);
		return builder;
	}
	
	public ConditionBuilder where(RawSql rawSql) {
		ConditionBuilder builder = new ConditionBuilder();
		Where where = new Where(rawSql);
		builder.setPreSqlFragment(where);
		where.setPreSqlFragment(pre());
//		setCurrentSqlFragment(where);
		return builder;
	}
	
	private OnBuilder buildOnBuilder(Table table,String type) {
		OnBuilder onBuilder = new OnBuilder(table,type);
		onBuilder.setPreSqlFragment(pre());
//		setCurrentSqlFragment(onBuilder);
		return onBuilder;
	}
	
	public GroupByBuilder groupBy(Column ... fields) {
		GroupByBuilder groupByBuilder = new GroupByBuilder();
		GroupBy groupBy = new GroupBy(fields);
		groupBy.setPreSqlFragment(pre());
		groupByBuilder.setPreSqlFragment(groupBy);
//		setCurrentSqlFragment(groupBy);
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
