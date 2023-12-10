package com.ms.datawise.distn.config;

import msjava.dbpool.datasource.DB2DataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5. Hibernate TransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;
@Configuration
@EnableTransactionManagement
@EnableAsync

public class DbConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource getDataSource() {
	
		DB2DataSource dataSource = new DB2DataSource();
		dataSource.setDatabase Server (env.getProperty("distn.datasource.server"));
		
		((DB2DataSource) dataSource).setKerberized (env.getProperty("distn.db.kerb.check", Boolean.class)); 
		((DB2DataSource) dataSource).setSchema (env.getProperty("distn.datasource.schema"));
		dataSource.setMinIdle (env.getProperty("distn.db.min.idle", Integer.class).intValue()); 
		dataSource.setMaxActive (env.getProperty("distn.db.max.active", Integer.class).intValue());
		dataSource.setPerformValidation (env.getProperty("distn.db.validation.check", Boolean.class)); 
		dataSource.setValidationTimeout (env.getProperty("distn.db.validation.timeout", Integer.class). intValue()); 
		dataSource.setPerformExpiration (env.getProperty("distn.db.expiration.check", Boolean.class));
		dataSource.setExpirationTimeout (env.getProperty("distn.db.expiration.timeout", Integer.class).intValue()); 
		return dataSource;
	}
	
	
	@Bean (name = "entityManagerFactory")
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean(); 
		sessionFactory.setDataSource (getDataSource());
		sessionFactory.setPackagesToScan (new String[] { "com.ms.datawise.distn.entity", com.ms.datawise.distn.model" });
		sessionFactory.setHibernate Properties (getHibernate Properties());
		return sessionFactory;
	}
	
	@Bean (name="dbTransactionManager")
	public Hibernate TransactionManager getHibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new Hibernate TransactionManager(); 
		transactionManager.setSessionFactory (getSessionFactory().getObject()); 
		return transactionManager;
	}
	
	@Bean
	public JdbcTemplate getHive JDBCTemplate() {
		JdbcTemplate hiveTemplate= new JdbcTemplate(); 
		hiveTemplate.setDataSource (getDataSource()); 
		return hiveTemplate;
	}
	
	private Properties getHibernate Properties () { 
		Properties properties = new Properties();
		properties.put (AvailableSettings. DIALECT, env.getRequired Property ("hibernate.dialect")); 
		properties.put (AvailableSettings.SHOW_SQL, env.getRequiredProperty("hibernate.show_sql")); 
		properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch_size"));
		//properties.put (AvailableSettings. HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2dd1.auto")); 
		properties.put (AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS,
			env.getRequiredProperty("hibernate.current.session.context.class"));
		//properties.put("hibernate.connection.autocommit", "true");
		properties.put (AvailableSettings.JDBC_TIME_ZONE, "UTC");
		return properties;
	}
}