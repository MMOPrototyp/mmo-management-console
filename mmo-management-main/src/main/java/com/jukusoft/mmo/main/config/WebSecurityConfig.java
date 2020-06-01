package com.jukusoft.mmo.main.config;

import com.jukusoft.mmo.main.security.JWTAuthorizationFilter;
import com.jukusoft.mmo.main.security.JwtAuthenticationEntryPoint;
import com.jukusoft.mmo.main.security.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true/*,
        securedEnabled = true,
        jsr250Enabled = true*/)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SessionService sessionService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //disable CSRF for REST api, else REST client cannot connect to REST api
        http.cors().and().csrf().disable();

        //disable X-Frame-Options for h2 console, which uses iframes
        http.headers().frameOptions().disable();

        http
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .exceptionHandling().accessDeniedHandler(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/", "/login", "/api/login", "/api/version", "/h2/**", "/swagger/**", "/swagger-*", "/swagger-ui/**", "/swagger-resources/**", "/csrf", "/v2/**", "/webjars/**", "/actuator", "/actuator/*", "/errors/*", "/error", "/pages/*", "/res/**", "/api/test")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), sessionService, tokenHeader, secret))
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .exceptionHandling().accessDeniedHandler(unauthorizedHandler);

        //enable cache control
        http.headers().cacheControl();
    }

    //see also: https://stackoverflow.com/questions/50486314/how-to-solve-403-error-in-spring-boot-post-request
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        source.registerCorsConfiguration("/pages/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
