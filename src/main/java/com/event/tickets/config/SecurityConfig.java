package com.event.tickets.config;

import com.event.tickets.filters.UserProvisioningFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserProvisioningFilter userProvisioningFilter) throws Exception {
        log.info( "Started securityFilterChain from Security config" );
        httpSecurity.authorizeHttpRequests( authorize ->
                        authorize.anyRequest().authenticated() )
                .csrf( csrf -> csrf.disable() )
                .sessionManagement( session ->
                        session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
                .oauth2ResourceServer( oAuth2 ->
                        oAuth2.jwt( Customizer.withDefaults() ) )
                .addFilterAfter( userProvisioningFilter, BearerTokenAuthenticationFilter.class );
        return httpSecurity.build();
    }
}
