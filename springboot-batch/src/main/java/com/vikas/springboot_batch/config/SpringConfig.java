package com.vikas.springboot_batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class SpringConfig {
	
	@Value("classpath:org/springframework/batch/core/schema-drop-sqlserver.sql")
	private Resource dataRepositoryTable;
	
	@Value("classpath:org/springframework/batch/core/schema-sqlserver.sql")
	private Resource dataRepositorySchema;
	
	@Bean
	public JobRepository createJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(getDataSource());
		factory.setTransactionManager(getTransactionManager());
		//factory.setIsolationLevelForCreate("SERIALIZATION");
		factory.setTablePrefix("XML_");
		factory.setMaxVarCharLength(1000);
		return factory.getObject();
	}
	
	@Bean
	public JobLauncher createJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(createJobRepository());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
	
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=SpringBatchApplicationDB;integratedSecurity=true");
		dataSource.setUsername("sa");
		dataSource.setPassword("system");
		return dataSource;
	}
	
	@Bean 
	public PlatformTransactionManager getTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager =  new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(getDataSource());
		return dataSourceTransactionManager;
	}
	
	@Bean
	public DataSourceInitializer dataSourceInitializer() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(dataRepositoryTable);
		databasePopulator.addScript(dataRepositorySchema);
		databasePopulator.setIgnoreFailedDrops(true);
		
		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(getDataSource());
		initializer.setDatabasePopulator(databasePopulator);
		
		return initializer;
	}

}
