package com.wlz.jsql.sql;

import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.fun.Fun1;

public class Function {
	public static Fun1 max(String cloumnName) {
		return new Fun1("max",cloumnName);
	}
	
	public static Fun1 max(Column cloumn) {
		return new Fun1("max",cloumn);
	}
	
	public static Fun1 avg(String cloumnName) {
		return new Fun1("avg",cloumnName);
	}
	
	public static Fun1 avg(Column cloumn) {
		return new Fun1("avg",cloumn);
	}
	
	public static Fun1 min(String cloumnName) {
		return new Fun1("min",cloumnName);
	}
	
	public static Fun1 min(Column cloumn) {
		return new Fun1("min",cloumn);
	}

	public static  Fun1 count(String cloumnName){
		return new Fun1("count",cloumnName);
	}
	public static  Fun1 count(Column cloumn){
		return new Fun1("count",cloumn);
	}


}
