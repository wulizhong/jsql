package com.wlz.jsql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wlz.jsql.util.CollectionUtils;

public class Record {

	private Map<Class<?>,Object> record = new HashMap<>();
	
	private List<Map<String,Object>> dbResultMap = new ArrayList<Map<String,Object>>();
	
	private List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
	
	public void put(Class<?> key,Object value) {
		record.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> clazz){
		
		Object value = record.get(clazz);
		if(value == null) {
			return new ArrayList<>();
		}
		return (List<T>) value;
	}
	
	public <T> T get(Class<T> clazz) {
		List<T> list = getList(clazz);
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		if(list.size()!=1) {
			throw new JSqlException("multiple results");
		}
		return list.get(0);
	}
	
	private long  total;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<Map<String, Object>> getDbResultMap() {
		return dbResultMap;
	}

	public List<Map<String, Object>> getResultMap() {
		return resultMap;
	}

	public Map<Class<?>, Object> getRecord() {
		return record;
	}
}
