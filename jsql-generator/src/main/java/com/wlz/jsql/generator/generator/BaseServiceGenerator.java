package com.wlz.jsql.generator.generator;

import com.wlz.jsql.generator.*;

import java.util.ArrayList;
import java.util.List;

public class BaseServiceGenerator extends Generator{

	private String packageName;

	private String baseDaoPackage;

	private Table table;

	private String entityPackage;

	public BaseServiceGenerator(String packageName, String baseDaoPackage, String entityPackage, Table table) {
		super();
		this.packageName = packageName;
		this.baseDaoPackage = baseDaoPackage;
		this.entityPackage = entityPackage;
		this.table = table;
	}



	private String prefix;



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
		
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		

		sb.append("import java.util.List;\r\n");

		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");


		sb.append("import "+entityPackage+"."+clazzName+";\r\n");
		sb.append("import "+ baseDaoPackage +"."+clazzName+"BaseDao;\r\n");


		fileName = clazzName+"BaseService.java";
		

		sb.append("public class "+clazzName+"BaseService{\r\n");
		
		sb.append("\t@Autowired\r\n");

		String dao = clazzName+"BaseDao "+clazzFieldName+"Dao";

		sb.append("\t"+dao+";\r\n");

		String baseDao = clazzFieldName+"Dao";

		sb.append(generateInsertCode(baseDao));
		sb.append(generateUpdateCode(baseDao));
		
		sb.append(generateDeleteCode(baseDao));
		sb.append(generateSelectListCode(baseDao));
		sb.append(generateSelectOneByIdCode(baseDao));
//		sb.append(generateSelectWhereCode(baseDao));
		sb.append("}");
		return sb.toString();
	}
//	private String generateSelectWhereCode(String dao){
//		StringBuffer sb = new StringBuffer();
//		String tableName = table.getName();
//		if(StringUtils.isNotEmpty(prefix)) {
//			tableName = tableName.substring(prefix.length());
//		}
//		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
//		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
//		sb.append("\tpublic FetchConditionBuilder<"+clazzName+"> selectFrom"+clazzName+"() {\r\n");
//		sb.append("\t\treturn "+dao+".selectFrom"+clazzName+"();\n\n");
//		sb.append("\t}\r\n");
//		return sb.toString();
//	}
	private String generateInsertCode(String dao) {
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		sb.append("\tpublic int save"+clazzName+"("+clazzName+" param) {\r\n");
		sb.append("\t\treturn "+dao+".insert"+clazzName+"(param);\n\n");
		sb.append("\t}\r\n");
		
		
		return sb.toString();
	}
	
	private String generateUpdateCode(String dao) {
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		sb.append("\tpublic int update"+clazzName+"("+clazzName+" param) {\r\n");
		sb.append("\t\treturn "+dao+".update"+clazzName+"(param);\n\n");

		sb.append("\t}\r\n");
		
		return sb.toString();
	}
	
	private String generateDeleteCode(String dao) {
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		List<Column> columns = table.getColumns();
		List<Column> ids = new ArrayList<Column>();
		for(Column c:columns) {
			if(c.isPrimaryKey()) {
				ids.add(c);
			}
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		sb.append("\tpublic int delete"+clazzName+"ById(");
		for(Column c:ids) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			Class<?> typeClass = TypeUtils.getClassName(c.getType());
			sb.append(typeClass.getSimpleName()+" "+columnName);

			if(c == ids.get(ids.size()-1)) {
				sb.append("){\r\n");
			}else {
				sb.append(", ");
			}
		}
		sb.append("\t\treturn "+dao+".delete"+clazzName+"ById(");
		for(Column c:ids) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			sb.append(columnName);
			if(c == ids.get(ids.size()-1)) {
				sb.append(");\r\n");
			}else {
				sb.append(", ");
			}
		}


		sb.append("\t}\r\n");
		
		return sb.toString();
	}
	
	private String generateSelectListCode(String dao) {
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		sb.append("\tpublic List<"+clazzName+"> query"+clazzName+"List("+clazzName+" "+clazzFieldName+") {\r\n");
		sb.append("\t\treturn "+dao+".select"+clazzName+"List("+clazzFieldName+");\n\n");
		sb.append("\t}\r\n");
		
		
		return sb.toString();
	}
	
	private String generateSelectOneByIdCode(String dao) {
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		
		List<Column> columns = table.getColumns();
		List<Column> ids = new ArrayList<Column>();
		for(Column c:columns) {
			if(c.isPrimaryKey()) {
				ids.add(c);
			}
		}
		
		sb.append("\tpublic "+clazzName+" query"+clazzName+"ById(");

		for(Column c:ids) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			Class<?> typeClass = TypeUtils.getClassName(c.getType());
			sb.append(typeClass.getSimpleName()+" "+columnName);

			if(c == ids.get(ids.size()-1)) {
				sb.append("){\r\n");
			}else {
				sb.append(", ");
			}
		}
		sb.append("\t\treturn "+dao+".select"+clazzName+"ById(");
		for(Column c:ids) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			sb.append(columnName);
			if(c == ids.get(ids.size()-1)) {
				sb.append(");\r\n");
			}else {
				sb.append(", ");
			}
		}

		sb.append("\t}\r\n");
		
		
		return sb.toString();
	}
	String fileName = "";
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}
}
