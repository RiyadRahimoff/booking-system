package com.bookflow.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        RateLimitRule rule = getRule(path);

        if (rule == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = getClientIp(request);

        String key = rule.name() + ":" + clientIp;

        boolean allowed = rateLimiterService.isAllowed(
                key,
                rule.capacity(),
                rule.duration()
        );

        if (!allowed) {
            response.setStatus(429);
            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                        "status": 429,
                        "message": "Too many requests. Please try again later."
                    }
                    """);

            return;
        }

        filterChain.doFilter(request, response);
    }

    private RateLimitRule getRule(String path) {

        return switch (path) {

            case "/auth/login" ->
                    new RateLimitRule(
                            "login",
                            5,
                            Duration.ofMinutes(1)
                    );

            case "/auth/register" ->
                    new RateLimitRule(
                            "register",
                            3,
                            Duration.ofMinutes(1)
                    );

            case "/auth/forgot-password" ->
                    new RateLimitRule(
                            "forgot-password",
                            3,
                            Duration.ofMinutes(5)
                    );

            case "/auth/verify" ->
                    new RateLimitRule(
                            "verify",
                            5,
                            Duration.ofMinutes(1)
                    );

            case "/admin/get/users" ->
                new RateLimitRule(
                        "getAllusers",
                        2,
                        Duration.ofSeconds(30)
                );

            default -> null;
        };
    }

    private String getClientIp(HttpServletRequest request) {

        String forwardedFor =
                request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }

    private record RateLimitRule(
            String name,
            int capacity,
            Duration duration
    ) {
    }
}