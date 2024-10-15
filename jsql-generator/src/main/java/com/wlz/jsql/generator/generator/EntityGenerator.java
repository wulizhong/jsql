package com.wlz.jsql.generator.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wlz.jsql.generator.Column;
import com.wlz.jsql.generator.FieldNameUtils;
import com.wlz.jsql.generator.StringUtils;
import com.wlz.jsql.generator.Table;
import com.wlz.jsql.generator.TypeUtils;

public class EntityGenerator extends Generator{
	
	private String packageName;
	
	private Table table;
	
	private String prefix;

	public EntityGenerator(String packageName, Table table) {
		super();
		this.packageName = packageName;
		this.table = table;
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
		Set<String> set = new HashSet<>();
		List<Column> columns = table.getColumns();
		for(Column c : columns) {
			set.add(TypeUtils.getClassName(c.getType()).getName());
		}
		for(String s : set) {
			sb.append("import "+s+";\r\n");
		}
		sb.append("import java.util.Objects;\r\n");
		sb.append("import java.io.Serializable;\r\n");

		
		String tableName = table.getName();
		
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzName = FieldNameUtils.underlineToHump(tableName, true);
		clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzName);

		fileName = clazzName+".java";
		sb.append("public class "+clazzName +" implements Serializable{\r\n");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\tprivate static final long serialVersionUID = "+System.currentTimeMillis()+"L;\r\n");
		
		for(Column c : columns) {
			
			Class<?> clazz = TypeUtils.getClassName(c.getType());
			
			sb.append("\t/**\r\n");
			sb.append("\t*"+c.getComment()+"\r\n");
			sb.append("\t**/\r\n");
			
			sb.append("\tprivate "+clazz.getSimpleName()+" "+FieldNameUtils.underlineToHump(c.getName(), true)+";\r\n");;
			
		}
		
		for(Column c : columns) {
			
			Class<?> clazz = TypeUtils.getClassName(c.getType());
			
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			String methodName = FieldNameUtils.toUpperCaseFirstOne(columnName);
			
			sb.append("\tpublic void set"+methodName+"("+clazz.getSimpleName()+" "+columnName+"){\r\n");
			
			sb.append("\t\tthis."+columnName+"="+columnName+";\r\n");
			
			sb.append("\t}\r\n");
			
			sb.append("\tpublic "+clazz.getSimpleName()+" get"+methodName+"(){\r\n");
			
			sb.append("\t\treturn this."+columnName+";\r\n");
			
			sb.append("\t}\r\n");
			
		}
		
		sb.append("\t@Override\r\n");
		sb.append("\tpublic int hashCode() {\r\n");

		List<String> fields = new ArrayList<>();
		List<String> equals = new ArrayList<>();
		for(Column c : columns) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			fields.add(columnName);
			
			equals.add("Objects.equals("+columnName+",other."+columnName+")");
		}
		sb.append("\t\t return Objects.hash("+StringUtils.join(fields, ",")+"); \r\n");
		sb.append("\t}\r\n");
		
		
		sb.append("\t@Override\r\n");
		sb.append("\tpublic boolean equals(Object obj) {\r\n");
		sb.append("\t\tif (this == obj)\r\n"
				+ "			return true;\r\n"
				+ "		if (obj == null)\r\n"
				+ "			return false;\r\n"
				+ "		if (getClass() != obj.getClass())\r\n"
				+ "			return false;\r\n");
		
		sb.append("\t\t"+clazzName+" other = ("+clazzName+")obj;\r\n");
		
		
		sb.append("\t\treturn "+StringUtils.join(equals, "&&")+";\r\n");
		sb.append("\t}\r\n");
		sb.append("}\r\n");
		
		
		return sb.toString();
	}

	String fileName = "";
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}


}
