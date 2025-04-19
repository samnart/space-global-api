// package com.samnart.space_global.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// // @EnableWebSecurity
// public class SecurityConfig {
    
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf().disable()
//             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//             .and()
//             .authorizeHttpRequests()
//             .requestMatchers("/api/auth/**").permitAll()
//             .requestMatchers("/api/properties/**").permitAll()
//             .anyRequest().authenticated();
        
//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }
