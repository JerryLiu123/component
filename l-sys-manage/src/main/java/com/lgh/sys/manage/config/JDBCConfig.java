package com.lgh.sys.manage.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JDBCConfig {
	private static Logger log = LoggerFactory.getLogger(JDBCConfig.class);

	@Autowired
	private DataSource dataSource;
	
	@Bean
	@ConditionalOnMissingBean
	public JdbcTemplate jdbcTemplate() {
		log.warn("-----因没有jdbcTemplate，所以初始化默认的JDBCTemplate-----");
		return new JdbcTemplate(dataSource);
	}
}
