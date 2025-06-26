package com.event.tickets.filters;

import com.event.tickets.domain.User;
import com.event.tickets.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info( "User is provisioning by filter by once per request filter" );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt jwt) {
            UUID keycloakId = UUID.fromString( jwt.getSubject() );
            if (!userRepository.existsById( keycloakId )) {
                User user = User.builder()
                        .id( keycloakId )
                        .name( jwt.getClaimAsString( "preferred_username" ) )
                        .email( jwt.getClaimAsString( "email" ) )
                        .build();

                userRepository.save( user );
            }
        }

        filterChain.doFilter( request, response );
    }
}
