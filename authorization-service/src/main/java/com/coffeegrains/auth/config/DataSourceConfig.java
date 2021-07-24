package com.coffeegrains.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
	
	@Value("${datasource.auth.url}")
	private String authDatasourceURL;
	
	@Value("${datasource.auth.username}")
	private String authDatasourceUsername;
	
	@Value("${datasource.auth.password}")
	private String authDatasourcePassword;
	
	@Value("${datasource.auth.classname}")
	private String authDataSourceClassName;
	
	@Value("${datasource.auth.maxPoolSize}")
	private Integer authDatasourceMaxPoolSize;
	
	@Value("${datasource.auth.idleTimeout}")
	private Long authDatasourceIdleTimeout;
	
	@Value("${datasource.auth.connectionTimeout}")
	private Long authDatasourceConnectionTimeout;
	
	@Bean(name = "authJdbcTemplate")
	public JdbcTemplate getAuthJdbcTemplate() {
		return new JdbcTemplate(authDataSource());
	}

	private HikariDataSource authDataSource() {
		final HikariDataSource datasource = new HikariDataSource();
		datasource.setJdbcUrl(authDatasourceURL);
		datasource.setUsername(authDatasourceUsername);
		datasource.setPassword(authDatasourcePassword);
		datasource.setDriverClassName(authDataSourceClassName);
		datasource.setMaximumPoolSize(authDatasourceMaxPoolSize);
		datasource.setIdleTimeout(authDatasourceIdleTimeout);
		datasource.setConnectionTimeout(authDatasourceConnectionTimeout);
        return datasource;
	}

}
