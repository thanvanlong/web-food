package com.example.serverlbee.config;


import com.example.serverlbee.config.filter.CustomAuthorizationFilter;
import com.example.serverlbee.config.filter.CustomeAuthenticationFilter;
import com.example.serverlbee.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomeAuthenticationFilter customeAuthenticationFilter = new CustomeAuthenticationFilter(authenticationManagerBean());
        customeAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.cors();
        http.authorizeRequests().antMatchers("/api/login/**", "/api/refresh/**", "/api/user/signup", "/api/user/active/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/product/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/user/forgot").permitAll();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/product/**", "/api/table/**")
                .hasAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/table/**")
                .access("hasAuthority(\"ROLE_USER\") or  hasAuthority(\"ROLE_ADMIN\")");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customeAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://foodauth-1551d.web.app/", "http://localhost"));
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
//        // setAllowCredentials(true) is important, otherwise:
//        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
