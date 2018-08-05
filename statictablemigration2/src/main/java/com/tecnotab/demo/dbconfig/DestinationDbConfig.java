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
        entityManagerFactoryRef = "ds2EntityManager",
        transactionManagerRef = "ds2TransactionManager",
        basePackages = {"com.tecnotab.demo.destrepository"}
        )
public class DestinationDbConfig {
	


	@Autowired
    private Environment env;

	
	@Primary
	@Bean
	@ConfigurationProperties(prefix="source.destinationdatasource")
    public DataSource ds2Datasource() { 
       // return DataSourceBuilder.create().build(); 
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("source.destinationdatasource.driver-class-name"));
	    dataSource.setUrl(env.getProperty("source.destinationdatasource.url"));
	    dataSource.setUsername(env.getProperty("source.destinationdatasource.username"));
	    dataSource.setPassword(env.getProperty("source.destinationdatasource.password"));

	    return dataSource;
    } 
	
	
	@Bean
    public LocalContainerEntityManagerFactoryBean ds2EntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds2Datasource());
 
        // Scan Entities in Package:
        em.setPackagesToScan(new String[] {"com.tecnotab.demo.entity"});
        em.setPersistenceUnitName("destination_database");
 
     // JPA & Hibernate
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
 
        em.setJpaVendorAdapter(vendorAdapter);
 
        HashMap<String, Object> properties = new HashMap<>();
 
        // JPA & Hibernate
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect.2"));
        properties.put("hibernate.show-sql", env.getProperty("spring.jpa.show_sql.2"));
 
        
        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet();
        return em;
    }
	
	@Bean
    public PlatformTransactionManager ds2TransactionManager() {
 
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds2EntityManager().getObject());
        return transactionManager;
    }
	
}
	