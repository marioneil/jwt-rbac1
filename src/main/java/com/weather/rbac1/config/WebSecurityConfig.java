package com.weather.rbac1.config;


import com.weather.rbac1.filter.AuthFilter;
import com.weather.rbac1.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
 private AppUserDetailsService appUserDetailsService;

    @Autowired
    private AuthEntry authEntry;

    @Bean
    public AuthFilter getAuthFilter(){
        return new AuthFilter();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(appUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf->csrf.disable())
                .exceptionHandling(e->e.authenticationEntryPoint(authEntry))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth->auth.requestMatchers("/api/auth/**").permitAll()
                           //     .requestMatchers("/api/v1/user/controller/**").permitAll()
                        .requestMatchers("/api/v1/department/**").permitAll().anyRequest().authenticated()
                );
        //TODO   .requestMatchers("/api/v1/user/controller").permitAll().anyRequest().authenticated()
                httpSecurity.authenticationProvider(getDaoAuthenticationProvider());
                httpSecurity.addFilterBefore(getAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

//    @Bean
//    public FilterRegistrationBean<AuthFilter> jwtFilter() {
//        FilterRegistrationBean<AuthFilter> filter = new FilterRegistrationBean<AuthFilter>();
//        filter.setFilter(getAuthFilter());
//        filter.addUrlPatterns("/api/v1/department/**");
//        return filter;
//    }

}
