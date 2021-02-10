package com.epam.esm.dao.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EntityScan("com.epam.esm")
public class PersistenceTestConfiguration {
}
