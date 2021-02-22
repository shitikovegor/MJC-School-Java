package com.epam.esm.configuration;

import com.epam.esm.security.converter.JwtUserInfoConverter;
import com.epam.esm.security.converter.KeycloakAuthorityConverter;
import com.epam.esm.security.filter.FilterExceptionHandler;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.USERNAME;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final FilterExceptionHandler filterExceptionHandler;

    @Autowired
    public SecurityConfiguration(FilterExceptionHandler filterExceptionHandler) {
        this.filterExceptionHandler = filterExceptionHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(oauth -> oauth
                        .antMatchers(HttpMethod.POST, "/oauth2/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/login1").permitAll()
                        .antMatchers(HttpMethod.POST, "/registration").permitAll()
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
                        .jwtAuthenticationConverter(authenticationConverter()))
                .addFilterBefore(filterExceptionHandler, BearerTokenAuthenticationFilter.class);
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> authenticationConverter() {
        JwtUserInfoConverter converter = new JwtUserInfoConverter();
        converter.setGrantedAuthoritiesConverter(new KeycloakAuthorityConverter());
        return converter;
    }
}
