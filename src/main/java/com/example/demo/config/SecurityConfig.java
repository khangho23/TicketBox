package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.filter.RestFilter;
import com.example.demo.service.StaffDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private StaffDetailsService staffDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/*", "/admin").hasRole("ADMIN")
                        .requestMatchers("/empl/*", "/empl").hasRole("EMPLOYEE")
                                .anyRequest().permitAll()
                        )
                .authenticationProvider(authenticationProvider())
                .formLogin((form) -> form
                        .loginPage("/admin/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .failureUrl("/admin/login?error=true")
                        .permitAll())
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedHandler((request, response, authException) -> {
                            response.sendRedirect("/access-denied");
                        })
                )
                .logout((logout) -> logout.logoutSuccessUrl("/admin/login"));

        http.addFilterAfter(new RestFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(staffDetailsService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }
}
