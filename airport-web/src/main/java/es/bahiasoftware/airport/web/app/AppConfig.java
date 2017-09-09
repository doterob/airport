package es.bahiasoftware.airport.web.app;

import java.io.IOException;
import java.rmi.UnexpectedException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import es.bahiasoftware.airport.business.service.AircraftImporter;
import es.bahiasoftware.airport.model.Aircraft;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "es.bahiasoftware.airport")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("es.bahiasoftware.airport.persistence")
@EnableJpaAuditing
public class AppConfig {

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
		bean.setDatabase(Database.H2);
		bean.setGenerateDdl(true);
		bean.setShowSql(true);
		return bean;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource);
		bean.setJpaVendorAdapter(jpaVendorAdapter);
		bean.setPackagesToScan("es.bahiasoftware.airport");
		return bean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	@Bean
	public AircraftImporter aircraftImporter() {
		return new AircraftImporter() {
			
			@Override
			public Aircraft find(String manufacturer, String model) {
				ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
					
					@Override
					public boolean hasError(ClientHttpResponse response) throws IOException {
						return !response.getStatusCode().is2xxSuccessful() || !response.getStatusCode().equals(HttpStatus.NOT_FOUND);
					}
					
					@Override
					public void handleError(ClientHttpResponse response) throws IOException {
						// TODO Auto-generated method stub
						
					}
				};
				
				final RestTemplate template = new RestTemplate();
				template.setErrorHandler(errorHandler);
				final ResponseEntity<Aircraft> response = template.getForEntity("http://localhost:55555/aircraft-import/Boeing/747", Aircraft.class);
				if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
					return null;
				}
				return response.getBody();
			}
		};
	}
}
