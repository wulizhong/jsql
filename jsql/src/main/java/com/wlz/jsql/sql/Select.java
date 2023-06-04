package com.wlz.jsql.sql;

import java.util.LinkedList;
import java.util.List;

public class Select extends SqlFragment{

	private SqlFragment [] sqlFragments;
	
	@Override
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		
		List<Object> list = new LinkedList<>();
		if(sqlFragments!=null) {
			for(SqlFragment fragment : sqlFragments) {
				list.addAll(fragment.paramters());
			}
		}
		return list;
	}

	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		
		StringBuffer sb = new StringBuffer("select ");

		if(sqlFragments == null||sqlFragments.length == 0) {
			sb.append(" * ");
		}else {
			for(int i = 0;i<sqlFragments.length;i++) {
				
				sb.append(sqlFragments[i].toSql());
				sb.append(" ");
				if(i!=sqlFragments.length-1) {
					sb.append(",");
				}
				
			}
		}
		
		String sql = sb.toString();
//		System.out.println(sql);
		return sql;
	}

	public Select() {
		
	}
	
	public Select(SqlFragment [] sqlFragments) {
		this.sqlFragments = sqlFragments;
	}
}
