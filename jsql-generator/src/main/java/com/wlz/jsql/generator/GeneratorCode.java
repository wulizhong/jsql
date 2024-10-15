package com.wlz.jsql.generator;

import com.wlz.jsql.generator.config.*;

public class GeneratorCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JSqlGenerator jsqlGenerator = new JSqlGenerator(
				"jdbc:mysql://localhost:3306/library?serverTimezone=GMT%2B8&useSSL=false&verifyServerCertificate=true",
				"root",
				"123456",
				"D:\\workspace\\jsql-test\\library\\");

		//表快速引用类包路径
		jsqlGenerator.setTPackage("com.example.library.t");

		//tb_book表代码配置
		GeneratorConfig.Builder builder = GeneratorConfig.createBuilder();
		//表配置，配置表前缀后生成代码时会将前缀去除
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_book","tb_"));
		//entity生成配置
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		//dto生成配置
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		//basedao生成配置
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		//dao生成配置
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		//生成
		jsqlGenerator.add(builder.build());
		//tb_catalog表代码配置
		builder = GeneratorConfig.createBuilder();
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_catalog","tb_"));
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		jsqlGenerator.add(builder.build());

		//tb_book_catalog表代码配置
		builder = GeneratorConfig.createBuilder();
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_book_catalog","tb_"));
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		jsqlGenerator.add(builder.build());

		//tb_reader表代码配置
		builder = GeneratorConfig.createBuilder();
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_reader","tb_"));
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		jsqlGenerator.add(builder.build());

		//tb_student_score表代码配置
		builder = GeneratorConfig.createBuilder();
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_student_score","tb_"));
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		jsqlGenerator.add(builder.build());

		jsqlGenerator.add(builder.build());
		jsqlGenerator.generate();

		System.out.println("生成成功!");
	}

}
