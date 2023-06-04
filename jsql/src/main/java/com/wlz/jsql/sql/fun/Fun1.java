package com.wlz.jsql.sql.fun;

import com.wlz.jsql.sql.database.Column;

public class Fun1 extends Fun{
	
	private String functionName;
	
	private String cloumnName;
	public Fun1(String functionName,String cloumnName) {
		this.functionName = functionName;
		this.cloumnName = cloumnName;
	}
	private Column field;
	public Fun1(String functionName,Column field) {
		this.functionName = functionName;
		this.field = field;
	}




	@Override
	public String toSql() {
		// TODO Auto-generated method stub
//		Object [] params = new Object[]{eqColumn,eqStr,gtColumn,gtStr,gteColumn,gteStr,ltColumn,ltStr,lteColumn,lteStr};
//		for(Object obj : params){
//			if(obj!=null){
//
//			}
//		}

		if(eqColumn!=null){
			return " "+functionName+"( "+cloumnName+" ) = "+eqColumn.getName();
		}else if(eqStr!=null){
			return " "+functionName+"( "+cloumnName+" ) = "+eqStr;
		}else if(gtColumn!=null){
			return " "+functionName+"( "+cloumnName+" ) > "+gtColumn.getName();
		}else if(gtStr!=null){
			return " "+functionName+"( "+cloumnName+" ) > "+gtStr;
		}else if(gteColumn!=null){
			return " "+functionName+"( "+cloumnName+" ) >= "+gteColumn.getName();
		}else if(gteStr!=null){
			return " "+functionName+"( "+cloumnName+" ) >= "+gteStr;
		}else if(ltColumn!=null){
			return " "+functionName+"( "+cloumnName+" ) < "+ltColumn.getName();
		}else if(ltStr!=null){
			return " "+functionName+"( "+cloumnName+" ) < "+ltStr;
		}else if(lteColumn!=null){
			return " "+functionName+"( "+cloumnName+" ) <= "+lteColumn.getName();
		}else if(lteStr!=null){
			return " "+functionName+"( "+cloumnName+" ) <= "+lteStr;
		}else if(alias!=null){
			return " "+functionName+"( "+cloumnName+" ) as "+alias;
		}else if(field == null) {
			return " "+functionName+"( "+cloumnName+" ) as "+cloumnName;
		}
		return " "+functionName+"( "+field.toSql()+" ) as "+field.getName();
	}

}
