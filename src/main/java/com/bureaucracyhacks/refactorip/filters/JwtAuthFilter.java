package com.bureaucracyhacks.refactorip.filters;

import com.bureaucracyhacks.refactorip.services.TokenService;
import com.bureaucracyhacks.refactorip.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;
        final String role;
        final String refreshToken;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        username = tokenService.extractUsername(jwtToken);
        System.out.println(tokenService.extractExpirationDate(jwtToken));
        role = tokenService.extractRole(jwtToken);
        log.info("JWT Token: " + jwtToken);
        log.info("Username: " + username);
        log.info("Role: " + role);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            response.setHeader("Authorization", "Bearer " + jwtToken);

            if(tokenService.isTokenValid(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("User is now authenticated");
            }
        }
        refreshToken = tokenService.refreshToken(jwtToken);
        System.out.println(tokenService.extractExpirationDate(refreshToken));
        response.setHeader("Authorization", "Bearer " + refreshToken);
        filterChain.doFilter(request, response);
    }
}