package com.wlz.jsql.springboot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jsql")
public class JSqlProperty {

	private boolean sqlPrint = true;
	private boolean paramPrint = true;
	private boolean rawSqlPrint = true;
	private boolean resultCountPrint = true;
	public boolean isSqlPrint() {
		return sqlPrint;
	}
	public void setSqlPrint(boolean sqlPrint) {
		this.sqlPrint = sqlPrint;
	}
	public boolean isParamPrint() {
		return paramPrint;
	}
	public void setParamPrint(boolean paramPrint) {
		this.paramPrint = paramPrint;
	}
	public boolean isRawSqlPrint() {
		return rawSqlPrint;
	}
	public void setRawSqlPrint(boolean rawSqlPrint) {
		this.rawSqlPrint = rawSqlPrint;
	}
	public boolean isResultCountPrint() {
		return resultCountPrint;
	}
	public void setResultCountPrint(boolean resultCountPrint) {
		this.resultCountPrint = resultCountPrint;
	}
	
	
}
