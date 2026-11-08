
package com.bookflow.config;

import com.bookflow.auth.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/auth/register",
            "/auth/login",
            "/auth/verify-email",
            "/auth/resend-verify",
            "/auth/refresh-token",
            "/auth/forgot-Password",
            "/auth/reset-Password",
            "/test/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        .requestMatchers(PUBLIC_ENDPOINTS)
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/auth/me")
                        .authenticated()

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/business/create"
                        )
                        .hasRole("OWNER")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/business/my-business"
                        )
                        .hasRole("OWNER")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/business/update/me/business"
                        )
                        .hasRole("OWNER")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/business/delete-me"
                        )
                        .hasRole("OWNER")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/business/{businessId}/images"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/business/{businessId}/images"
                        )
                        .hasRole("OWNER")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/business/{businessId}/images/{imageId}/cover"
                        )
                        .hasRole("OWNER")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/business/{businessId}/images/{imageId}"
                        )
                        .hasRole("OWNER")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/business/all-business",
                                "/business/{id}"
                        )
                        .permitAll()

                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
