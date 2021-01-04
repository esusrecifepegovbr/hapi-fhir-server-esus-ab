package br.com.gointerop.hapi.fhir;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import br.com.gointerop.hapi.fhir.controller.ControllerFactory;
import br.com.gointerop.hapi.fhir.controller.ControllerPatient;
import br.com.gointerop.hapi.fhir.provider.ProviderBundle;
import br.com.gointerop.hapi.fhir.provider.ProviderPatient;
import br.com.gointerop.hapi.fhir.repository.IRepository;
import br.com.gointerop.hapi.fhir.repository.PersistenceProperties;
import br.com.gointerop.hapi.fhir.repository.RepositoryPatient;
import br.com.gointerop.hapi.fhir.service.IService;
import br.com.gointerop.hapi.fhir.service.ServicePatient;
import ca.uhn.fhir.context.ConfigurationException;
import ca.uhn.fhir.jpa.config.BaseJavaConfigR4;
import ca.uhn.fhir.jpa.search.DatabaseBackedPagingProvider;

@Configuration
@EnableTransactionManagement
public class FhirServerConfigR4 extends BaseJavaConfigR4 {
	@Autowired
	private DataSource myDataSource;

	/**
	 * We override the paging provider definition so that we can customize the
	 * default/max page sizes for search results. You can set these however you
	 * want, although very large page sizes will require a lot of RAM.
	 */
	@Override
	public DatabaseBackedPagingProvider databaseBackedPagingProvider() {
		DatabaseBackedPagingProvider pagingProvider = super.databaseBackedPagingProvider();
		pagingProvider.setDefaultPageSize(HapiProperties.getDefaultPageSize());
		pagingProvider.setMaximumPageSize(HapiProperties.getMaximumPageSize());
		return pagingProvider;
	}

	@Override
	@Bean()
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean retVal = super.entityManagerFactory();
		retVal.setPersistenceUnitName("HAPI_PU");

		try {
			retVal.setDataSource(myDataSource);
		} catch (Exception e) {
			throw new ConfigurationException("Could not set the data source due to a configuration issue", e);
		}

		retVal.setJpaProperties(HapiProperties.getJpaProperties());
		return retVal;
	}

	@Bean
	@Primary
	public JpaTransactionManager hapiTransactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager retVal = new JpaTransactionManager();
		retVal.setEntityManagerFactory(entityManagerFactory);
		return retVal;
	}

	@Bean
	public JdbcTemplate jdbcTemplateESUS() {
		return new JdbcTemplate(dataSourceESUS());
	}

	@Bean
	public DataSource dataSourceESUS() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(PersistenceProperties.getDataSourceDriver());
		config.setJdbcUrl(PersistenceProperties.getDataSourceUrl());
		config.setUsername(PersistenceProperties.getDataSourceUsername());
		config.setPassword(PersistenceProperties.getDataSourcePassword());
		config.setAutoCommit(true);
		config.setMaximumPoolSize(PersistenceProperties.getDataSourceMaxPoolSize());

		HikariDataSource ds = new HikariDataSource(config);

		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManagerESUS() {
		return new DataSourceTransactionManager(dataSourceESUS());
	}

	
	@Bean
	public ControllerFactory controllerFactory() {
		return new ControllerFactory();
	}
	
	@Bean
	public ProviderPatient providerPatient() {
		return new ProviderPatient();
	}
	
	@Bean
	public ControllerPatient controllerPatient() {
		return new ControllerPatient();
	}
	
	@Bean
	public IService<Patient> servicePatient() {
		return new ServicePatient();
	}
	
	@Bean
	public IRepository<Patient> repositoryPatient() {
		return new RepositoryPatient(jdbcTemplateESUS());
	}
	
	@Bean ProviderBundle providerBundle() {
		return new ProviderBundle();
	}
}
