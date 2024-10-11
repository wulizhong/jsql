package com.wlz.jsql.generator.generator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.wlz.jsql.generator.Column;
import com.wlz.jsql.generator.FieldNameUtils;
import com.wlz.jsql.generator.StringUtils;
import com.wlz.jsql.generator.Table;
import com.wlz.jsql.generator.TypeUtils;

public class BaseDaoGenerator extends Generator{

	private String packageName;
	
	private String tablesPackage;
	
	private Table table;
	
	private String entityPackage;
	
	public BaseDaoGenerator(String packageName, String tablesPackage,String entityPackage, Table table) {
		super();
		this.packageName = packageName;
		this.tablesPackage = tablesPackage;
		this.table = table;
		this.entityPackage = entityPackage;
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
		
		sb.append("import java.util.ArrayList;\r\n");
		sb.append("import java.util.List;\r\n");
		sb.append("import com.wlz.jsql.DSL;\r\n");
		sb.append("import com.wlz.jsql.Sql;\r\n");
		sb.append("import com.wlz.jsql.SqlExecutor;\r\n");
		sb.append("import com.wlz.jsql.sql.InsertResult;\r\n");
		sb.append("import com.wlz.jsql.sql.builder.FromBuilder;\r\n");
		sb.append("import com.wlz.jsql.sql.builder.ConditionBuilder;\r\n");
		sb.append("import com.wlz.jsql.sql.database.Column;\r\n");
		sb.append("import com.wlz.jsql.sql.database.Id;\r\n");
		sb.append("import com.wlz.jsql.FetchConditionBuilder;\r\n");
		sb.append("import org.springframework.stereotype.Repository;\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");

		sb.append("import "+entityPackage+"."+clazzName+";\r\n");
		sb.append("import static "+tablesPackage+".T.*;\r\n");


		fileName = clazzName+"BaseDao.java";
		
		sb.append("@Repository\r\n");
		sb.append("public class "+clazzName+"BaseDao{\r\n");
		
		sb.append("\t@Autowired\r\n");
		sb.append("\tSqlExecutor sqlExecutor;\r\n");
		
		sb.append(generateInsertCode());
		sb.append(generateUpdateCode());
		
		sb.append(generateDeleteCode());
		sb.append(generateSelectListCode());
		sb.append(generateSelectOneByIdCode());
		sb.append(generateSelectWhereCode());
		sb.append("}");
		return sb.toString();
	}
	private String generateSelectWhereCode(){
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		sb.append("\tpublic FetchConditionBuilder<"+clazzName+"> selectFrom"+clazzName+"() {\r\n");
		String tb = convertStringCase(tableName);
		sb.append("\t\t FromBuilder fromBuilder = DSL.select("+tb+".ALL).from("+tb+");\r\n");
		sb.append("\t\t FetchConditionBuilder<"+clazzName+"> fetchConditionBuilder = new FetchConditionBuilder<>(sqlExecutor,"+clazzName+".class,fromBuilder);\r\n");
		sb.append("\t\t return fetchConditionBuilder;\r\n");


		sb.append("\t}\r\n");
		return sb.toString();
	}
	private String generateInsertCode() {
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		sb.append("\tpublic int insert"+clazzName+"("+clazzName+" param) {\r\n");
		
		sb.append("\t\tList<Column> columns = new ArrayList<>();\r\n");
		sb.append("\t\tList<Object> values = new ArrayList<>();\r\n");
		
		List<Column> columns = table.getColumns();
		
		for(Column c:columns) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			String methodName = FieldNameUtils.toUpperCaseFirstOne(columnName);
			
			sb.append("\t\tif(param.get"+methodName+"()!=null){\r\n");
			
			sb.append("\t\t\t columns.add("+convertStringCase(tableName)+"."+convertStringCase(c.getName())+");\r\n");
			sb.append("\t\t\t values.add(param.get"+methodName+"());\r\n");
			
			sb.append("\t\t}\r\n");
			
		}
		
		sb.append("\t\tSql sql = DSL.insertInto("+convertStringCase(tableName)+", columns.toArray(new Column[] {})).values(values.toArray());\r\n");
		
		sb.append("\t\tInsertResult ir = sqlExecutor.insert(sql);\r\n");
		
		sb.append("\t\tif(ir.getId()!=null) {\r\n");
		
		for(Column c : columns) {
			if(c.isAutoIncrement()) {
				String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
				String methodName = FieldNameUtils.toUpperCaseFirstOne(columnName);

				Class<?> idType = TypeUtils.getClassName(c.getType());

				if(idType == BigDecimal.class||idType == BigInteger.class){
					sb.append("\t\t\tparam.set"+methodName+"(new BigBigDecimal(ir.getId().longValue()));\r\n");
				}else{

					String convertType = "";
					if(idType == Long.class){
						convertType = ".longValue()";
					}else if(idType == Integer.class){
						convertType = ".intValue()";
					}else if(idType == Short.class){
						convertType = ".shortValue()";
					}
					sb.append("\t\t\tparam.set"+methodName+"(ir.getId()"+convertType+");\r\n");

				}

				break;
			}
		}
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn ir.getRowCount();\r\n");
		sb.append("\t}\r\n");
		
		
		return sb.toString();
	}
	
	private String generateUpdateCode() {
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		sb.append("\tpublic int update"+clazzName+"("+clazzName+" param) {\r\n");
		
		sb.append("\t\tList<Column> columns = new ArrayList<>();\r\n");
		sb.append("\t\tList<Object> values = new ArrayList<>();\r\n");
		
		List<Column> columns = table.getColumns();
		
		for(Column c:columns) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			String methodName = FieldNameUtils.toUpperCaseFirstOne(columnName);
			
			String getMethod = "param.get"+methodName+"()";

			String columnField = convertStringCase(tableName)+"."+convertStringCase(c.getName());
			sb.append("\t\tif("+getMethod+"!=null&&!("+columnField+" instanceof Id)){\r\n");
			
			sb.append("\t\t\t columns.add("+columnField+");\r\n");
			sb.append("\t\t\t values.add(param.get"+methodName+"());\r\n");
			
			sb.append("\t\t}\r\n");
			
		}
		
		sb.append("\t\tSql sql = DSL.update("+convertStringCase(tableName)+").set(columns.toArray(new Column[] {}), values.toArray())\r\n");

		
		List<Column> ids = new ArrayList<Column>();
		for(Column c:columns) {
			if(c.isPrimaryKey()) {
				ids.add(c);
			}
		}
		boolean isFirst = true;
		for(Column c:ids) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			String methodName = FieldNameUtils.toUpperCaseFirstOne(columnName);
			String getMethod = "param.get"+methodName+"()";
			if(isFirst) {
				isFirst = false;
				sb.append("\t\t      .where(");
			}else {
				sb.append("\t\t      .and(");
			}
			sb.append(convertStringCase(tableName)+"."+convertStringCase(c.getName())+".eq("+getMethod+"))");

			if(c == ids.get(ids.size()-1)) {
				sb.append(";\r\n");
			}else {
				sb.append("\r\n");
			}
		}
		
		
		
		sb.append("\t\treturn sqlExecutor.update(sql);\r\n");
		sb.append("\t}\r\n");
		
		return sb.toString();
	}
	
	private String generateDeleteCode() {
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
		
		boolean isFirst = true;
		sb.append("\t\tSql sql = DSL.deleteFrom("+convertStringCase(tableName)+")\r\n");
		whereCode(sb, tableName, ids, isFirst);

		sb.append("\t\treturn sqlExecutor.update(sql);\r\n");
		sb.append("\t}\r\n");
		
		return sb.toString();
	}
	
	private String generateSelectListCode() {
		StringBuffer sb = new StringBuffer();
		String tableName = table.getName();
		if(StringUtils.isNotEmpty(prefix)) {
			tableName = tableName.substring(prefix.length());
		}
		String clazzFieldName = FieldNameUtils.underlineToHump(tableName, true);
		String clazzName = FieldNameUtils.toUpperCaseFirstOne(clazzFieldName);
		sb.append("\tpublic List<"+clazzName+"> select"+clazzName+"List("+clazzName+" "+clazzFieldName+") {\r\n");
		sb.append("\t\tConditionBuilder conditionBuilder = null;\r\n") ;
		
		sb.append("\t\tFromBuilder fromBuilder = DSL.select("+convertStringCase(tableName)+"."+convertStringCase("ALL")+").from("+convertStringCase(tableName)+");\r\n");
		
		
		List<Column> columns = table.getColumns();
		
		for(Column c:columns) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			String methodName = FieldNameUtils.toUpperCaseFirstOne(columnName);
			
			String getMethod = clazzFieldName+".get"+methodName+"()";
			
			sb.append("\t\tif("+getMethod+"!=null){\r\n");

			sb.append("\t\t\tif(conditionBuilder == null){\r\n");
			sb.append("\t\t\t\tconditionBuilder = fromBuilder.where("+convertStringCase(tableName)+"."+convertStringCase(c.getName())+".eq("+getMethod+"));\r\n");
			sb.append("\t\t\t}else {\r\n");
			sb.append("\t\t\t\tconditionBuilder = conditionBuilder.and("+convertStringCase(tableName)+"."+convertStringCase(c.getName())+".eq("+getMethod+"));\r\n");
			sb.append("\t\t\t}\r\n");
			sb.append("\t\t}\r\n");
			
		}
		
		sb.append("\t\tList<"+clazzName+"> list = sqlExecutor.selectList(conditionBuilder, "+clazzName+".class);\r\n");
		sb.append("\t\treturn list;\r\n");
		sb.append("\t}\r\n");
		
		
		return sb.toString();
	}
	
	private String generateSelectOneByIdCode() {
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
		
		sb.append("\tpublic "+clazzName+" select"+clazzName+"ById(");
		
		for(Column c:ids) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			Class<?> typeClass = TypeUtils.getClassName(c.getType());
			sb.append(typeClass.getSimpleName()+" "+columnName);
			
			if(c == ids.get(ids.size()-1)) {
				sb.append("){\r\n");
			}else {
				sb.append(",");
			}
		}
		
		sb.append("\t\tSql sql = DSL.select("+convertStringCase(tableName)+"."+convertStringCase("ALL")+").from("+convertStringCase(tableName)+")\r\n");
		boolean isFirst = true;
		whereCode(sb, tableName, ids, isFirst);

		sb.append("\t\treturn sqlExecutor.selectOne(sql, "+clazzName+".class);\r\n");
		
		sb.append("\t}\r\n");
		
		
		return sb.toString();
	}

	private void whereCode(StringBuffer sb, String tableName, List<Column> ids, boolean isFirst) {
		for(Column c:ids) {
			String columnName = FieldNameUtils.underlineToHump(c.getName(), true);
			if(isFirst) {
				isFirst = false;
				sb.append("\t\t      .where(");
			}else {
				sb.append("\t\t      .and(");
			}
			sb.append(convertStringCase(tableName)+"."+convertStringCase(c.getName())+".eq("+columnName+"))");

			if(c == ids.get(ids.size()-1)) {
				sb.append(";\r\n");
			}else {
				sb.append("\r\n");
			}
		}
	}

	String fileName = "";
	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}
}
