package com.wlz.jsql.generator;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;

public class TypeUtils {

	public static Class<?> getClassName(int type) {
		
		if(type == Types.BIGINT) {
			return Long.class;
		}else if(type == Types.VARCHAR) {
			return String.class;
		}else if(type == Types.DECIMAL) {
			return BigDecimal.class;
		}else if(type == Types.DATE) {
			return Date.class;
		}else if(type == Types.TIMESTAMP) {
			return Date.class;
		}else if(type == Types.INTEGER) {
			return Integer.class;
		}else if(type == Types.DOUBLE) {
			return Double.class;
		}else if(type == Types.FLOAT) {
			return Float.class;
		}else if(type == Types.TINYINT) {
			return Short.class;
		}
		
		return null;
	}
}
