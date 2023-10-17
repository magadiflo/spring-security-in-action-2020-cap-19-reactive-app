package com.magadiflo.reactive.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.time.LocalTime;

@Configuration
public class ProjectConfig {

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("12345")
                .authorities("ROLE_ADMIN")
                .build();

        return new MapReactiveUserDetailsService(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange(authorize -> authorize
                        .anyExchange().access(this.getAuthorizationDecision())
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    private ReactiveAuthorizationManager<AuthorizationContext> getAuthorizationDecision() {
        return (authentication, context) -> {
            String path = this.getRequestPath(context);
            boolean restrictedTime = LocalTime.now().isAfter(LocalTime.NOON);

            if (path.equals("/api/v1/greetings/hello")) {
                return authentication
                        .map(a -> a.getAuthorities().stream()
                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")))
                        .map(isAdmin -> isAdmin && !restrictedTime)
                        .map(AuthorizationDecision::new);
            }

            return Mono.just(new AuthorizationDecision(false));
        };
    }

    private String getRequestPath(AuthorizationContext context) {
        return context.getExchange().getRequest().getPath().toString();
    }

}
