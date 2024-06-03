package com.wlz.jsql.sql;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.Sql;
import com.wlz.jsql.SqlContext;
import com.wlz.jsql.sql.database.Table;

public abstract class SqlFragment implements Sql{
	
	private SqlFragment preSqlFragment;
	
//	private SqlFragment nextSqlFragment;

	protected SqlFragment pre() {
		return preSqlFragment;
	}

	public void setPreSqlFragment(SqlFragment preSqlFragment) {
		this.preSqlFragment = preSqlFragment;
	}

//	protected SqlFragment next() {
//		return nextSqlFragment;
//	}
//
//	public void setNextSqlFragment(SqlFragment nextSqlFragment) {
//		this.nextSqlFragment = nextSqlFragment;
//	}

	public List<Object> paramters() {
		// TODO Auto-generated method stub
		SqlFragment preSqlFragment = pre();
		if(preSqlFragment!=null) {
			List<Object> preParamterList = preSqlFragment.paramters();
			return preParamterList;
		}
		return new ArrayList<>();
	}
	
	public String toSql(SqlContext sqlContext) {
		return null;
	}
	
	public List<Table> tables(){
		return preSqlFragment == null? null: preSqlFragment.tables();
	}
	
}
