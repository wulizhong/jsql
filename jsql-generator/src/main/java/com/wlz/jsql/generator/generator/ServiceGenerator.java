package com.wlz.jsql.generator.generator;

import com.wlz.jsql.generator.FieldNameUtils;
import com.wlz.jsql.generator.StringUtils;
import com.wlz.jsql.generator.Table;

public class ServiceGenerator extends Generator{

	private String packageName;

	private String baseServicePackageName;

	private Table table;

	private String prefix;

	private String tablesPackage;
	private String daoPackage;
	public ServiceGenerator(String packageName, String baseServicePackageName, Table table, String tablesPackage,String daoPackage) {
		super();
		this.packageName = packageName;
		this.table = table;
		this.baseServicePackageName = baseServicePackageName;
		this.tablesPackage = tablesPackage;
		this.daoPackage = daoPackage;

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
		sb.append("import org.springframework.stereotype.Service;\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");

		sb.append("import "+ baseServicePackageName +"."+clazzName+"BaseService;\r\n");

		sb.append("import static "+tablesPackage+".T.*;\r\n");
		sb.append("import "+daoPackage+"."+clazzName+"Dao;\r\n");

		String service = "Service";

		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		fileName = clazzName+service+".java";
		sb.append("@Service\r\n");
		sb.append("public class "+clazzName+service +" extends "+clazzName+"BaseService{\r\n");
		sb.append("\t@Autowired\r\n");

		String dao = clazzName+"Dao "+clazzFieldName+"Dao";
		sb.append("\t"+dao+";\r\n");
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
