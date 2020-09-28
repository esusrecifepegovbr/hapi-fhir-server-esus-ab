package br.com.gointerop.hapi.fhir.repository;

import java.lang.reflect.InvocationTargetException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class JdbcTemplateHikariDataSource {
	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static JdbcTemplate jdbcTemplate;
    
    public static HikariDataSource getDataSource() {
    	if(JdbcTemplateHikariDataSource.ds == null) {
			JdbcTemplateHikariDataSource.config.setDriverClassName(PersistenceProperties.getDataSourceDriver());
			JdbcTemplateHikariDataSource.config.setJdbcUrl( PersistenceProperties.getDataSourceUrl() );
			JdbcTemplateHikariDataSource.config.setUsername( PersistenceProperties.getDataSourceUsername() );
			JdbcTemplateHikariDataSource.config.setPassword( PersistenceProperties.getDataSourcePassword() );
			JdbcTemplateHikariDataSource.config.setAutoCommit(false);
			JdbcTemplateHikariDataSource.config.setMaximumPoolSize( PersistenceProperties.getDataSourceMaxPoolSize() );
			
			JdbcTemplateHikariDataSource.ds = new HikariDataSource(JdbcTemplateHikariDataSource.config);
		}
    	
    	return JdbcTemplateHikariDataSource.ds;
    }
	
	public static JdbcTemplate getJdbcTemplate() {
		if(JdbcTemplateHikariDataSource.jdbcTemplate == null) {
			JdbcTemplateHikariDataSource.jdbcTemplate = new JdbcTemplate(JdbcTemplateHikariDataSource.getDataSource());
		}
		
		return JdbcTemplateHikariDataSource.jdbcTemplate;
	}
}
