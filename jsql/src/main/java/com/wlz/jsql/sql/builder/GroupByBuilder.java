package com.wlz.jsql.sql.builder;

import com.wlz.jsql.sql.Having;
import com.wlz.jsql.sql.Limit;
import com.wlz.jsql.sql.Order;
import com.wlz.jsql.sql.OrderBy;
import com.wlz.jsql.sql.SqlFragment;

public class GroupByBuilder extends SqlFragmentBuilder{

	public OrderByBuilder orderBy(Order ...orders) {
		OrderByBuilder orderByBuilder = new OrderByBuilder();
		OrderBy orderBy = new OrderBy(orders);
		orderBy.setPreSqlFragment(pre());
		orderByBuilder.setPreSqlFragment(orderBy);
		return orderByBuilder;
	}
	
	public ConditionBuilder having(SqlFragment field) {
		ConditionBuilder conditionBuilder = new ConditionBuilder();
		Having having = new Having(field);
		having.setPreSqlFragment(pre());
		conditionBuilder.setPreSqlFragment(having);
		return conditionBuilder;
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
