package com.wlz.jsql.generator.generator;

import com.wlz.jsql.generator.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DaoGenerator extends Generator{

	private String packageName;

	private String baseDaoPackageName;

	private Table table;

	private String prefix;

	private String tpackage;
	public DaoGenerator(String packageName,String baseDaoPackageName,String tpackage, Table table) {
		super();
		this.packageName = packageName;
		this.table = table;
		this.baseDaoPackageName = baseDaoPackageName;
		this.tpackage = tpackage;
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
		String tableName = table.getName();

		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzName = FieldNameUtils.underlineToHump(tableName, true);
		clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzName);
		sb.append("import org.springframework.stereotype.Repository;\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		sb.append("import com.wlz.jsql.SqlExecutor;\r\n");
		sb.append("import "+baseDaoPackageName+"."+clazzName+"BaseDao;\r\n");
		sb.append("import static "+tpackage+".T.*;\r\n");
		sb.append("import static com.wlz.jsql.DSL.*;\r\n");
		String dao = "Dao";
		

		fileName = clazzName+dao+".java";
		sb.append("@Repository\r\n");
		sb.append("public class "+clazzName+dao +" extends "+clazzName+"BaseDao{\r\n");
		sb.append("\t@Autowired\r\n");
		sb.append("\tSqlExecutor sqlExecutor;\r\n");
		sb.append("\t\t\r\n");
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
