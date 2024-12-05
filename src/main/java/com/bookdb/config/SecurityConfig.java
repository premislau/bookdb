package com.bookdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //CSRF setting are default i.e. token is needed for state-changing HTTP methods

    @Bean
    public SecurityFilterChain httpMethods(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                ).
                authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.GET, "/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/**").authenticated())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }


}
