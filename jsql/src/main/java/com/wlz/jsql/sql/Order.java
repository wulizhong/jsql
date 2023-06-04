package com.wlz.jsql.sql;

import com.wlz.jsql.sql.database.Column;

public class Order {
	
	private Column column;
	
	private String order;
	
	private static final String DESC = "desc";
	
	private static final String ASC = "asc";
	
	public static Order asc(Column column) {
		return new Order(column,ASC);
	}
	
	public static Order desc(Column column) {
		return new Order(column,DESC);
	}

	private Order() {
		
	}
	private Order(Column column, String order) {
		super();
		this.column = column;
		this.order = order;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	
}
