package com.vodafone.ecommerce.config;

import com.vodafone.ecommerce.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig
{
    @Autowired
    UserDetailsServiceImpl service;

    @Autowired
    CustomLoginFailureHandler loginFailureHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(service)
                .authorizeHttpRequests((request)-> request.requestMatchers(
                        "/login", "/logout", 
                        "/logout-success", "/register", "/verify",
                        "/login-failed", "/password-reset").permitAll()
                        .requestMatchers(HttpMethod.POST, "/password-reset")
                        .permitAll()
                        )

                // login and logout-success pages can be edited
                .formLogin(form -> form.loginPage("/login")
                    .defaultSuccessUrl("/", true)                    
                    .failureHandler(loginFailureHandler)
                    )
                .logout((form) -> form.logoutUrl("/logout").logoutSuccessUrl("/logout-success").permitAll())
                .authorizeHttpRequests((auth) -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}
