package com.wlz.jsql.sql;

import java.util.LinkedList;
import java.util.List;

import com.wlz.jsql.util.CollectionUtils;

public class ConditionSqlFragment extends SqlFragment{

	protected SqlFragment sqlFragment;
	

	public List<Object> paramters() {
		// TODO Auto-generated method stub
		List<Object> paramterList = new LinkedList<>(); 
		paramterList.addAll(sqlFragment.paramters());
		SqlFragment preSqlFragment = pre();
		if(preSqlFragment!=null) {
			List<Object> preParamterList = preSqlFragment.paramters();
			if(CollectionUtils.isNotEmpty(preParamterList)) {
				preParamterList.addAll(paramterList);
				paramterList = preParamterList;
			}
		}
		return paramterList;
	}
}
