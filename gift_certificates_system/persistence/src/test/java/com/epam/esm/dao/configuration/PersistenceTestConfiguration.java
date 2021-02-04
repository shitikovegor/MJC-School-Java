package com.epam.esm.dao.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EntityScan(basePackages = "com.epam.esm")
public class PersistenceTestConfiguration {
}
