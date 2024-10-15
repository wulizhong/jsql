package com.wlz.jsql.generator.generator;

import java.util.Arrays;
import java.util.List;

import com.wlz.jsql.generator.Column;
import com.wlz.jsql.generator.FieldNameUtils;
import com.wlz.jsql.generator.StringUtils;
import com.wlz.jsql.generator.Table;

public class TableGenerator extends Generator{
	
	private String packageName;
	
	private Table table;
	
	private String prefix;
	
	private String entityPackageName;

	private String classPackage;

	public TableGenerator(String packageName,String entityPackageName, Table table) {
		super();
		this.packageName = packageName;
		this.table = table;
		this.entityPackageName = entityPackageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String generateCode() {
		
		StringBuffer sb = new StringBuffer("package "+packageName+";\r\n");
		
		sb.append("import com.wlz.jsql.sql.database.Column;\r\n");
		sb.append("import com.wlz.jsql.sql.database.Id;\r\n");
		sb.append("import com.wlz.jsql.sql.database.Table;\r\n");
		
		
		String tableName = table.getName();
		
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		

		String tableSuffix = "Table";
		
		String clazzName = FieldNameUtils.underlineToHump(tableName, true);
		clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzName);
		
		fileName = clazzName+tableSuffix+".java";

		classPackage = packageName+"."+clazzName+tableSuffix;
		
		sb.append("public class "+clazzName+tableSuffix +" extends Table {\r\n");
		sb.append("\tpublic "+clazzName+tableSuffix +"(String tableName, String tableAlias){\r\n");
		sb.append("\t\tsuper(tableName, tableAlias);\r\n");
		sb.append("\t}\r\n");
		
		
		sb.append("\tpublic static final Column "+convertStringCase("ALL")+" = new Column(\""+convertTableAlias(convertStringCase(tableName))+"\",\"*\");\r\n");
		
		sb.append("\tpublic static final Column "+convertStringCase("ALL")+"(String tableAlias) {\r\n");
		sb.append("\t\treturn new Column(tableAlias,\"*\");\r\n");
		sb.append("\t}\r\n");
		
		
		sb.append("\tpublic static final "+clazzName+tableSuffix+" "+convertStringCase(tableName)+" = new "+clazzName+tableSuffix+"(\""+table.getName()+"\",\""+convertTableAlias(convertStringCase(tableName))+"\"); \r\n");
		
		List<Column> columns = table.getColumns();
		
		for(Column c : columns) {
			
			String columnName = c.getName().toUpperCase();
//			String fieldName = FieldNameUtils.underlineToHump(c.getName(), true);
			if(c.isPrimaryKey()) {
				
				sb.append("\tpublic static final Id "+convertStringCase(columnName.toUpperCase())+" = new Id(\""+convertTableAlias(convertStringCase(tableName))+"\",\""+convertStringCase(columnName)+"\","+c.getType()+");\r\n");
				sb.append("\tpublic static final Id "+convertStringCase(columnName.toUpperCase())+" (String tableAlias) {\r\n");
				sb.append("\t\treturn new Id(tableAlias,\""+columnName+"\","+c.getType()+");\r\n");
				sb.append("\t}\r\n");
				
			}else {
				
				sb.append("\tpublic final Column "+convertStringCase(columnName)+" = new Column(\""+convertTableAlias(convertStringCase(tableName))+"\",\""+convertStringCase(columnName)+"\","+c.getType()+");\r\n");
				sb.append("\tpublic final Column "+convertStringCase(columnName)+" (String tableAlias) {\r\n");
				sb.append("\t\treturn new Column(tableAlias,\""+convertStringCase(columnName)+"\","+c.getType()+");\r\n");
				sb.append("\t}\r\n");
				
			}
			
		}
		sb.append("\tpublic Class<?> getEntityType(){\r\n");
		sb.append("\t\t return "+entityPackageName+"."+clazzName+".class;\r\n");
		sb.append("\t}\r\n");
		
		sb.append("\tpublic boolean isGenerateKey(){\r\n");
		sb.append("\t\t return "+table.isAutoIncrement()+";\r\n");
		sb.append("\t}\r\n");
		
		sb.append("}\r\n");
		
		
		return sb.toString();
	}

	private String convertTableAlias(String alias){

		String [] keywords=new String[]{"select","from","order","where","and","or","between","on","user","like","limit","rowcount"};

		for(String key : keywords){
			if(key.equalsIgnoreCase(alias)){
				return alias+"_";
			}
		}
		return alias;
	}

	String fileName = "";
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}
	public String getClassPackage() {
		return classPackage;
	}
	
}
