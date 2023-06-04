package com.wlz.jsql.sql.fun;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.sql.SqlFragment;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.database.Table;

public  class Fun extends SqlFragment{

	protected String alias;
	public Fun as(String alias){
		this.alias = alias;

		return this;
	}
	protected Column gteColumn;
	/**
	 * 大于等于
	 * @param gteColumn
	 * @return
	 */
	public Fun gte(Column gteColumn){
		this.gteColumn = gteColumn;
		return this;
	}

	protected String gteStr;
	/**
	 * 大于等于
	 * @param gteStr
	 * @return
	 */
	public Fun gte(String gteStr){
		this.gteStr = gteStr;
		return this;
	}

	protected Column ltColumn;
	/**
	 * 小于
	 * @param ltColumn
	 * @return
	 */
	public Fun lt(Column ltColumn){
		this.ltColumn = ltColumn;
		return this;
	}

	protected String ltStr;
	/**
	 * 小于
	 * @param ltStr
	 * @return
	 */
	public Fun lt(String ltStr){
		this.ltStr = ltStr;
		return this;
	}

	protected Column lteColumn;

	/**
	 * 小于等于
	 * @param lteColumn
	 * @return
	 */
	public Fun lte(Column lteColumn){
		this.lteColumn = lteColumn;
		return this;
	}

	protected String lteStr;

	/**
	 * 小于等于
	 * @param lteStr
	 * @return
	 */
	public Fun lte(String lteStr){
		this.lteStr = lteStr;
		return this;
	}

	protected Column gtColumn;
	/**
	 * 大于
	 * @param gtColumn
	 * @return
	 */
	public Fun gt(Column gtColumn){
		this.gtColumn = gtColumn;
		return this;
	}

	protected String gtStr;
	/**
	 * 大于
	 * @param gtStr
	 * @return
	 */
	public Fun gt(String gtStr){
		this.gtStr = gtStr;
		return this;
	}

	protected Column eqColumn;
	/**
	 * 等于
	 * @param eqColumn
	 * @return
	 */
	public Fun eq(Column eqColumn){
		this.eqColumn = eqColumn;
		return this;
	}

	protected String eqStr;
	/**
	 * 等于
	 * @param eqStr
	 * @return
	 */
	public Fun eq(String eqStr){
		this.eqStr = eqStr;
		return this;
	}
	@Override
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<Table> tables() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

}
