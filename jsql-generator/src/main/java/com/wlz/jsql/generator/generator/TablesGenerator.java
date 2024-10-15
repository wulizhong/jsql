package com.wlz.jsql.generator.generator;

import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.generator.FieldNameUtils;
import com.wlz.jsql.generator.StringUtils;

public class TablesGenerator extends Generator{

	private String packageName;
	
	private List<String> prefixList = new ArrayList<>();
	
	private List<String> tables;

	private List<String> classPackageList;

	public TablesGenerator(String packageName, List<String> tables) {
		super();
		this.packageName = packageName;
		this.tables = tables;
	}
	
	public void add(String prefix) {
		prefixList.add(prefix);
	}
	
	public String generateCode() {
		

		StringBuffer sb = new StringBuffer("package "+packageName+";\r\n");
		if(classPackageList!=null){
			for(String str : classPackageList){
				sb.append("import "+str+";\r\n");
			}
		}
		sb.append("public class T {\r\n");
		
		for(String table : tables) {
			
			String prefix = "";
			
			for(String p : prefixList) {
				
				if(table.startsWith(p.toLowerCase())||table.startsWith(p.toUpperCase())) {
					prefix = p;
					break;
				}
				
			}
			String tableSuffix = "Table";
			
			
			String tableName = table;
			if(StringUtils.isNotEmpty(prefix)) {
				tableName = tableName.substring(prefix.length());
			}
			String clazzName = FieldNameUtils.underlineToHump(tableName, true);
			clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzName);
			sb.append("\tpublic static final "+clazzName+tableSuffix+" "+convertStringCase(tableName)+" = "+clazzName+tableSuffix+"."+convertStringCase(tableName)+";\r\n");
			
			
			sb.append("\tpublic static final "+clazzName+tableSuffix+" "+convertStringCase(tableName)+"(String tableAlias) {\r\n");
			
			sb.append("\t\treturn new "+clazzName+tableSuffix+"(\""+convertStringCase(table)+"\",tableAlias);\r\n");
			
			sb.append("\t}\r\n");
			
		}
		
		sb.append("}\r\n");
		
		
		
		return sb.toString();
	}

	public List<String> getPrefixList() {
		return prefixList;
	}

	public void setPrefixList(List<String> prefixList) {
		this.prefixList = prefixList;
	}

	String fileName = "T.java";
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}

	public List<String> getClassPackageList() {
		return classPackageList;
	}

	public void setClassPackageList(List<String> classPackageList) {
		this.classPackageList = classPackageList;
	}
}
