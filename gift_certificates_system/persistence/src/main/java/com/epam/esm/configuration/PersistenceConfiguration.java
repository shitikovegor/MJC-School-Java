package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Class {@code PersistenceConfiguration} contains spring configuration for persistence.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
public class PersistenceConfiguration {

    @Value("${database.driver}")
    private String driverClassName;
    @Value("${database.url}")
    private String url;
    @Value("${database.user}")
    private String user;
    @Value("${database.password}")
    private String password;
    @Value("${database.max_pool_size}")
    private Integer maxPoolSize;

    /**
     * Bean {@code PropertySourcesPlaceholderConfigurer} will set path for properties of database
     *
     * @return the property sources placeholder configurer
     */
    @Bean
    @Profile("prod")
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
        props.setLocations(new ClassPathResource("property/database.prod.properties"));
        return props;
    }

    @Bean
    @Profile("dev")
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurerDev() {
        PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
        props.setLocations(new ClassPathResource("property/database.dev.properties"));
        return props;
    }

    /**
     * Bean {@code HikariDataSource} will be use as data source
     *
     * @return the data source
     */
    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(maxPoolSize);

        return dataSource;
    }

    /**
     * Bean {@code JdbcTemplate} will be use for queries
     *
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
