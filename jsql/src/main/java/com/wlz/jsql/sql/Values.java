package com.wlz.jsql.sql;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.wlz.jsql.util.CollectionUtils;
import com.wlz.jsql.util.StringUtils;

public class Values extends SqlFragment{

	private Object values[];

	public Values(Object... values) {
		super();
		this.values = values;
	}
	
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		List<Object> paramterList = new LinkedList<>();
		
		if(values!=null) {
			for(Object obj : values) {
				paramterList.add(obj);
			}
		}
		
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

	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		if(values!=null) {
			sb.append(" values (");
			List<String> strList = new ArrayList<>();
			
			for(int i = 0;i<values.length;i++) {
				strList.add("?");
			}
			sb.append(StringUtils.join(strList, ","));
			sb.append(")");
		}
		sb.insert(0,pre().toSql());
		return sb.toString();
	}

	
	
	
}
