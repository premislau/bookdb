package com.bookdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //CSRF setting are default i.e. token is needed for state-changing HTTP methods

    @Bean
    public SecurityFilterChain httpMethods(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.GET, "/**").permitAll())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.POST, "/**").authenticated())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.PUT, "/**").authenticated())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.DELETE, "/**").authenticated());

        return httpSecurity.build();
    }


}
