package com.epam.esm.configuration;

import com.epam.esm.security.RestAuthenticationEntryPoint;
import com.epam.esm.security.converter.JwtUserInfoConverter;
import com.epam.esm.security.converter.KeycloakAuthorityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Class {@code SecurityConfiguration} contains spring configuration for security.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(oauth2 -> oauth2
                        .antMatchers(HttpMethod.POST, "/users").permitAll()
                        .antMatchers(HttpMethod.GET, "/gift-certificates/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/tags/**").hasAnyRole("user", "admin")
                        .antMatchers(HttpMethod.GET, "/users/{\\d+}").hasAnyRole("user", "admin")
                        .antMatchers(HttpMethod.GET, "/orders/{\\d+}").hasAnyRole("user", "admin")
                        .antMatchers(HttpMethod.GET, "/orders/users/{\\d+}").hasAnyRole("user", "admin")
                        .antMatchers(HttpMethod.POST, "/orders/**").hasAnyRole("user", "admin")
                        .antMatchers("/**").hasRole("admin")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt()
                        .jwtAuthenticationConverter(authenticationConverter())
                        .and().authenticationEntryPoint(authenticationEntryPoint));
    }

    /**
     * Bean {@code Converter} will be use as token converter
     *
     * @return the converter
     */
    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> authenticationConverter() {
        JwtUserInfoConverter converter = new JwtUserInfoConverter();
        converter.setGrantedAuthoritiesConverter(new KeycloakAuthorityConverter());
        return converter;
    }
}
