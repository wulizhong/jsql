package com.wlz.jsql.sql;

public class InsertResult {
	
	

	int rowCount = 0;
	
	Object id;
	
	

	public InsertResult() {
	}

	public InsertResult(int rowCount) {
		super();
		this.rowCount = rowCount;
	}

	public InsertResult(int rowCount, Object id) {
		super();
		this.rowCount = rowCount;
		this.id = id;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}
	
	
}
