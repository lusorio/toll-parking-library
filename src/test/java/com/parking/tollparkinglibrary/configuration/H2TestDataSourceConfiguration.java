package com.parking.tollparkinglibrary.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Embedded HSQL server containing the testing data set
 */
@Configuration
@Profile("test")
@EnableJpaRepositories(basePackages = "com.parking.tollparkinglibrary.repositories")
//@PropertySource("classpath:application-test.properties")
@EnableTransactionManagement
public class H2TestDataSourceConfiguration
{
    @Bean
    @Qualifier("configurationDataSource")
    public DataSource configurationDataSource()
    {
        return new EmbeddedDatabaseBuilder()
                       .setType(EmbeddedDatabaseType.H2)
                       .addScript("classpath:test_dataset.sql")
                       .build();
    }
}
