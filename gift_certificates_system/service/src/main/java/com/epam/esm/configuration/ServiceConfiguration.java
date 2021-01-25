package com.epam.esm.configuration;

import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

/**
 * Class {@code ServiceConfiguration} contains spring configuration for service.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class ServiceConfiguration {

    /**
     * Bean {@code ModelMapper} will be use as model mapper
     *
     * @return the model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    /**
     * Bean {@code HibernateTransactionManager} will be use for transactions
     *
     * @param sessionFactory the session factory
     * @return the Hibernate transaction manager
     */
    @Bean(name = "transactionManager")
    public HibernateTransactionManager TransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }
}
