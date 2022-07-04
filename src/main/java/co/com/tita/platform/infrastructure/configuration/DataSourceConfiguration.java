package co.com.tita.platform.infrastructure.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import co.com.tita.platform.modules.common.EbusinessConstant;

import org.springframework.beans.factory.annotation.Qualifier;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = EbusinessConstant.ENTITY_MANAGER_FACTORY,
  basePackages = { EbusinessConstant.BASE_PACKAGES }
)
public class DataSourceConfiguration {

	@Primary
	@Bean(name = EbusinessConstant.JPA_DATASOURCE_NAME)
	@ConfigurationProperties(prefix = EbusinessConstant.DATASOURCE_USER)
	public DataSource dataSource() {
		HikariDataSource ds = (HikariDataSource) DataSourceBuilder.create().build();	
		ds.setConnectionTestQuery("SELECT 1");
		return ds;
	}	
	
	
	  @Primary
	  @Bean(name = EbusinessConstant.ENTITY_MANAGER_FACTORY)
	  public LocalContainerEntityManagerFactoryBean 
	  entityManagerFactory(
	    EntityManagerFactoryBuilder builder,
	    @Qualifier(EbusinessConstant.JPA_DATASOURCE_NAME) DataSource dataSource
	  ) {
	    return builder
	      .dataSource(dataSource)
	      .packages(EbusinessConstant.ENTITY_PACKAGES)
	      .persistenceUnit(EbusinessConstant.UNIT_PERSITENCE)
	      .build();
	  }
	    
	  @Primary
	  @Bean(name = EbusinessConstant.TRANSACTION_MANAGER)
	  public PlatformTransactionManager transactionManager(
	    @Qualifier(EbusinessConstant.ENTITY_MANAGER_FACTORY) EntityManagerFactory 
	    entityManagerFactory
	  ) {
	    return new JpaTransactionManager(entityManagerFactory);
	  }	
	
	
}
