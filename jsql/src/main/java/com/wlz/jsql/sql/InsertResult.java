package com.wlz.jsql.sql;

public class InsertResult {
	
	

	int rowCount = 0;
	
	Number id;
	
	

	public InsertResult() {
	}

	public InsertResult(int rowCount) {
		super();
		this.rowCount = rowCount;
	}

	public InsertResult(int rowCount, Number id) {
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

	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}
	
	
}
