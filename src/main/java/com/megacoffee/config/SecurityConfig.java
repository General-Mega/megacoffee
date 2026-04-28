package com.megacoffee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login",
                    "/register",
                    "/password-reset-request",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/static/**",
                    "/login"
                ).permitAll()
                .requestMatchers("/members/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(24 * 60 * 60)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                //.deleteCookies("JSESSIONID", "remember-me")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return bcrypt.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword == null) {
                    return false;
                }
                String raw = rawPassword.toString();
                if (encodedPassword.startsWith("{noop}")) {
                    return raw.equals(encodedPassword.substring("{noop}".length()));
                }
                if (encodedPassword.startsWith("{bcrypt}")) {
                    return bcrypt.matches(raw, encodedPassword.substring("{bcrypt}".length()));
                }
                if (encodedPassword.startsWith("{")) {
                    return raw.equals(encodedPassword);
                }
                return bcrypt.matches(raw, encodedPassword) || raw.equals(encodedPassword);
            }
        };
    }
}
