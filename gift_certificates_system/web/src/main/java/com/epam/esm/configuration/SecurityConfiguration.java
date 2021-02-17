package com.epam.esm.configuration;

import com.epam.esm.security.filter.FilterExceptionHandler;
import com.epam.esm.security.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenFilter jwtTokenFilter;
    private final FilterExceptionHandler filterExceptionHandler;

    @Autowired
    public SecurityConfiguration(JwtTokenFilter jwtTokenFilter, FilterExceptionHandler filterExceptionHandler) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.filterExceptionHandler = filterExceptionHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/gift-certificates/**").permitAll()
                .antMatchers(HttpMethod.GET, "/tags/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/users/{\\d+}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/{\\d+}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/users/{\\d+}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/orders/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler, JwtTokenFilter.class);
    }
}
