package com.bureaucracyhacks.refactorip.config;

import com.bureaucracyhacks.refactorip.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
            .authorizeHttpRequests((authorize) ->
                    {
                        try {
                            authorize
                                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                                    .requestMatchers("/api/user-service/**").permitAll()
                                    .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                                        .requestMatchers("/api/doc/**").permitAll()
                                        .requestMatchers("/api/image/**").permitAll()
                                        .requestMatchers("/api/institutions/**").permitAll()
                                        .requestMatchers("/api/tasks/**").permitAll()
                                        .requestMatchers("/api/directions").permitAll()
                                            .anyRequest().permitAll()
                                    .and()
                                    .sessionManagement()
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                    .and()
                                    .authenticationProvider(authenticationProvider)
                                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        return http.build();
    }

}
