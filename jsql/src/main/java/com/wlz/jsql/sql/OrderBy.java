package com.wlz.jsql.sql;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.SqlContext;
import com.wlz.jsql.util.StringUtils;

public class OrderBy extends SqlFragment{

	private Order[] orders;
	
	public OrderBy(Order ...orders) {
		this.orders = orders;
	}

	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		if(orders == null) {
			return pre().toSql(sqlContext);
		}
		List<String> sqlFragmentList = new ArrayList<>();
		for(Order order : orders) {
			sqlFragmentList.add(order.getColumn().toSql(sqlContext)+" "+order.getOrder());
		}
		StringBuffer sb = new StringBuffer(" order by "+StringUtils.join(sqlFragmentList, ",")+" ");
		sb.insert(0, pre().toSql(sqlContext));
		return sb.toString();
	}

}
