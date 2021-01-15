package com.epam.esm.configuration;

import com.epam.esm.converter.OrderSortConverter;
import com.epam.esm.converter.TypeSortConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class {@code PersistenceConfiguration} contains spring configuration for web.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfiguration implements WebMvcConfigurer {
    @Value("i18n/messages")
    private String pathName;

    @Value("UTF-8")
    private String encodingType;

    /**
     * Bean {@code DataSource} will be use as message source
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(pathName);
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding(encodingType);
        return source;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OrderSortConverter());
        registry.addConverter(new TypeSortConverter());
    }
}
