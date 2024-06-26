package com.nagarro.OAuth2.config;

import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/index.html", "/oauth2/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin.loginPage("/")
//                                .permitAll()
//                )
//                .oauth2Login(oauthLogin ->
//                        oauthLogin
//                                .loginPage("/")
//                                .userInfoEndpoint(userInfoEndPoint ->
//                                        userInfoEndPoint.oidcUserService(new OidcUserService())
//                                ));
//        return httpSecurity.build();
//    }
//}
