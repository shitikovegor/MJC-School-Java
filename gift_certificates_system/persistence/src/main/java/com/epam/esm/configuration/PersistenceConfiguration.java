package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.io.IOException;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

/**
 * Class {@code PersistenceConfiguration} contains spring configuration for persistence.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
public class PersistenceConfiguration {
    private Environment environment;
    private HikariDataSource dataSource;

    @Autowired
    public PersistenceConfiguration(Environment environment, HikariDataSource dataSource) {
        this.environment = environment;
        this.dataSource = dataSource;
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.epam.esm.entity");
        sessionFactoryBean.setHibernateProperties(setProperties());
        sessionFactoryBean.afterPropertiesSet();

        return sessionFactoryBean.getObject();
    }

    private Properties setProperties() {
        Properties properties = new Properties();
        properties.setProperty(DIALECT, environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.setProperty(CURRENT_SESSION_CONTEXT_CLASS, environment.getProperty("spring.jpa.properties" +
                ".hibernate.current_session_context_class"));
        properties.setProperty(SHOW_SQL, environment.getProperty("spring.jpa.show-sql"));
        properties.setProperty(HBM2DDL_AUTO, environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.setProperty(JDBC_TIME_ZONE, environment.getProperty("spring.jpa.properties.hibernate.jdbc.time_zone"));
        return properties;
    }
}
