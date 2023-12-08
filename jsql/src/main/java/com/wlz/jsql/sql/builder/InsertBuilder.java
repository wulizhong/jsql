package com.wlz.jsql.sql.builder;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.SqlContext;
import com.wlz.jsql.sql.Select;
import com.wlz.jsql.sql.Values;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.database.Table;
import com.wlz.jsql.util.StringUtils;

public class InsertBuilder extends SqlFragmentBuilder{

	private Table table;
	
	private Column[] fields;

	public InsertBuilder(Table table,Column ...fields) {
		super();
		this.table = table;
		this.fields = fields;
	}
	public InsertBuilder(Table table) {
		super();
		this.table = table;
	}

	public SelectBuilder select(Column... fields) {
		SelectBuilder fromBuilder = new SelectBuilder();
		Select select = new Select(fields);
		select.setPreSqlFragment(this);
		fromBuilder.setPreSqlFragment(select);
		return fromBuilder;
	}
	
	public Values values(Object ...values) {
		Values val = new Values(values);
		val.setPreSqlFragment(this);
		return val;
	}
	@Override
	public String toSql(SqlContext sqlContext) {
		// TODO Auto-generated method stub
		
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(table.getTableName());
		sb.append(" ");
		
		if(fields!=null) {
			List<String> fieldList = new ArrayList<>();
			
			for(Column f : fields) {
				fieldList.add(f.getName());
			}
			sb.append(" ( ");
			sb.append(StringUtils.join(fieldList, ","));
			sb.append(" ) ");
			
		}
		
		return sb.toString();
	}
	@Override
	public List<Table> tables() {
		// TODO Auto-generated method stub
		List<Table> tables = new ArrayList<>();
		tables.add(table);
		return tables;
	}
	@Override
	public List<Object> paramters() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
