package com.wlz.jsql.sql.database;

public abstract class Table {

	private String tableAlias;
	
	private String tableName;
	
	private boolean generateKey = false;;

	public Table( String tableName,String  tableAlias) {
		this.tableAlias = tableAlias;
		this.tableName = tableName;
	}
	
	public Table( String tableName,String  tableAlias,boolean generateKey) {
		this.tableAlias = tableAlias;
		this.tableName = tableName;
		this.generateKey = generateKey;
	}

	public String getTableAlias() {
		return tableAlias;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	
	
	public boolean isGenerateKey() {
		return generateKey;
	}

	public void setGenerateKey(boolean generateKey) {
		this.generateKey = generateKey;
	}

	public abstract Class<?> getEntityType();
}
