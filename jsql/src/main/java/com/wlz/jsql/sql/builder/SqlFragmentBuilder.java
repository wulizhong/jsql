package com.wlz.jsql.sql.builder;

import java.util.List;

import com.wlz.jsql.sql.SqlFragment;
import com.wlz.jsql.sql.database.Table;

public class SqlFragmentBuilder extends SqlFragment{

	private SqlFragment currentSqlFragment;
	
	@Override
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		return currentSqlFragment == null? pre().paramters():currentSqlFragment.paramters();
	}

	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		return currentSqlFragment == null? pre().toSql():currentSqlFragment.toSql();
	}
	
	public List<Table> tables(){
		return currentSqlFragment == null? pre().tables():currentSqlFragment.tables();
	}

	protected SqlFragment currentSqlFragment() {
		return currentSqlFragment;
	}

//	protected void setCurrentSqlFragment(SqlFragment currentSqlFragment) {
//		this.currentSqlFragment = currentSqlFragment;
//	}
	
	
}
