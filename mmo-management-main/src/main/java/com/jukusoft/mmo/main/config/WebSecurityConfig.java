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

    /*@Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
    }*/

    /*@Deprecated
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }*/

    /*@Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationTokenFilter();
    }*/

    /*@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

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
                .antMatchers("/", "/angular-frontend/**", "/react-frontend/**", "/login", "/api/login", "/api/version", "/h2/**", "/swagger/**", "/swagger-*", "/swagger-ui/**", "/swagger-resources/**", "/csrf", "/v2/**", "/webjars/**", "/actuator", "/actuator/*", "/errors/*", "/error", "/pages/*", "/res/**", "/api/test")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), sessionService, tokenHeader, secret))
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .exceptionHandling().accessDeniedHandler(unauthorizedHandler);

        //http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
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

    /*@Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                //allow access to / and /login to any user (also unauthentificated ones)
                .antMatchers("/login", "/actuator", "/actuator/*", "/errors/*", "/error", "/pages/*", "/res/**", "/avatar/**", "/api/auth/create-token", "/api/auth/verify-token", "/api/check-client-version", "/api/register")
                .permitAll()
                //lock out any unauthentificated users from any other page
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .realmName(Config.get("Login", "realmName"))
                //.formLogin()
                //.successHandler(mySuccessHandler)
                //.failureHandler(myFailureHandler)
                .and()
                //.exceptionHandling().accessDeniedPage("/errors/access-denied")
                //.and()
                .logout();
    }*/

    /*@Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        //register own custom authentification provider
        auth.authenticationProvider(authProvider);
    }*/

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
