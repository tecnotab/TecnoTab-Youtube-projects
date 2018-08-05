package com.tecnotab.demo.dbconfig;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySources({ @PropertySource("classpath:application.properties") })
@EnableJpaRepositories(
        entityManagerFactoryRef = "ds1EntityManager",
        transactionManagerRef = "ds1TransactionManager",
        basePackages = {"com.tecnotab.demo.repository"}
        )
public class SourceDbConfig {
	


	@Autowired
    private Environment env;

	

	@Bean(name = "sourceDataSource") 
	@ConfigurationProperties(prefix="source.sourcedatasource")
    public DataSource ds1Datasource() { 
       // return DataSourceBuilder.create().build(); 
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("source.sourcedatasource.driver-class-name"));
	    dataSource.setUrl(env.getProperty("source.sourcedatasource.url"));
	    dataSource.setUsername(env.getProperty("source.sourcedatasource.username"));
	    dataSource.setPassword(env.getProperty("source.sourcedatasource.password"));

	    return dataSource;
    } 
	
	
	@Bean
    public LocalContainerEntityManagerFactoryBean ds1EntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds1Datasource());
 
        // Scan Entities in Package:
        em.setPackagesToScan(new String[] {"com.tecnotab.demo.entity"});
        em.setPersistenceUnitName("source_database");
 
     // JPA & Hibernate
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
 
        em.setJpaVendorAdapter(vendorAdapter);
 
        HashMap<String, Object> properties = new HashMap<>();
 
        // JPA & Hibernate
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect.1"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show_sql.1"));
      //  properties.put("hibernate.naming-strategy", env.getProperty("spring.jpa.hibernate.naming-strategy"));
 
        
        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet();
        return em;
    }
	
	@Bean
    public PlatformTransactionManager ds1TransactionManager() {
 
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds1EntityManager().getObject());
        return transactionManager;
    }
	
}
	