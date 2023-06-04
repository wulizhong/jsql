package com.wlz.jsql.springboot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.wlz.jsql.JdbcTemplateSqlExecutor;
import com.wlz.jsql.SqlExecutor;

//@ConditionalOnBean(JdbcTemplate.class)
@Configuration
@EnableConfigurationProperties(value = JSqlProperty.class)
public class JSqlAutoConfiguration {

	@Autowired
	private JSqlProperty jsqlProperty;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@ConditionalOnMissingBean(SqlExecutor.class)
	@Bean
	public SqlExecutor sqlExecutor() {
		SqlExecutor sqlExecutor = new JdbcTemplateSqlExecutor(jdbcTemplate,jsqlProperty);
		return sqlExecutor;
	}
	
}

