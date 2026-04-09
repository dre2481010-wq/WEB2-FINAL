package com.example.productcrud.config;


import com.example.productcrud.controller.AuthController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthPageFilter authPageFilter;

    public SecurityConfig(final AuthPageFilter authPageFilter){
        this.authPageFilter = authPageFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        http.addFilterBefore(authPageFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/login", "/register", "/css/**", "/js/**" )
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .formLogin(
                        form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/products",true)
                                .permitAll())
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("login?logout")
                                .permitAll());
        return http.build();
    }
}
