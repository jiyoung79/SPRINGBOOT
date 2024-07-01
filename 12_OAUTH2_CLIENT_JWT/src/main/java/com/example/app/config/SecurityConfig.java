package com.example.app.config;

import com.example.app.config.auth.exceptionHandler.CustomAccessDeniedHandler;
import com.example.app.config.auth.exceptionHandler.CustomAuthenticationEntryPoint;
import com.example.app.config.auth.jwt.JwtAuthorizationFilter;
import com.example.app.config.auth.jwt.JwtProperties;
import com.example.app.config.auth.jwt.JwtTokenProvider;
import com.example.app.config.auth.loginHandler.CustomAuthenticationFailureHandler;
import com.example.app.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.example.app.config.auth.loginHandler.OAuth2JwtLoginSuccessHandeler;
import com.example.app.config.auth.logoutHandler.CustomLogoutHandler;
import com.example.app.config.auth.logoutHandler.CustomLogoutSuccessHandler;
import com.example.app.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //웹요청 처리
    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {

        //CSRF 비활성화
        http.csrf((config)->{config.disable();});

        //요청 URL별 접근 제한
        http.authorizeHttpRequests((auth)->{
            auth.requestMatchers("/","/join","/login").permitAll();
            auth.requestMatchers("/user").hasRole("USER");
            auth.requestMatchers("/member").hasRole("MEMBER");
            auth.requestMatchers("/admin").hasRole("ADMIN");
            auth.anyRequest().authenticated();
        });

        //로그인
        http.formLogin((login)->{
            login.permitAll();
            login.loginPage("/login");
            login.successHandler(new CustomLoginSuccessHandler(jwtTokenProvider));  // 생성자 인자로 전
            login.failureHandler(new CustomAuthenticationFailureHandler());
        });

        //로그아웃
        http.logout((logout)->{
            logout.permitAll();
            logout.logoutUrl("/logout");
            logout.addLogoutHandler(customLogoutHandler());
            logout.logoutSuccessHandler(customLogoutSuccessHandler());
            // JWT
            logout.deleteCookies("JSESSIONID", JwtProperties.COOKIE_NAME);
            logout.invalidateHttpSession(true);
        });

        //예외처리
        http.exceptionHandling((ex)->{
            ex.accessDeniedHandler(new CustomAccessDeniedHandler());
            ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        });

        //REMEMBER_ME
        http.rememberMe((rm)->{
            rm.key("rememberMeKey");
            rm.rememberMeParameter("remember-me");
            rm.alwaysRemember(false);
            rm.tokenValiditySeconds(30*30);
            rm.tokenRepository(tokenRepository());
        });

        //OAUTH2-CLIENT
        http.oauth2Login((oauth2)->{
            oauth2.loginPage("/login");
            oauth2.successHandler(new OAuth2JwtLoginSuccessHandeler(jwtTokenProvider));

        });

        // SESSION INVALIDATE
        http.sessionManagement(
                httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
        );

        // JWT
        http.addFilterBefore(new JwtAuthorizationFilter(userRepository, jwtTokenProvider),
                BasicAuthenticationFilter.class);

        return http.build();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }



//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        userDetailsManager
//                    .createUser(User
//                            .withUsername("user")
//                            .password(passwordEncoder.encode("1234"))
//                            .roles("USER")
//                            .build()
//                    );
//        userDetailsManager
//                .createUser(User
//                        .withUsername("member")
//                        .password(passwordEncoder.encode("1234"))
//                        .roles("MEMBER")
//                        .build()
//                );
//        userDetailsManager
//                .createUser(User
//                        .withUsername("admin")
//                        .password(passwordEncoder.encode("1234"))
//                        .roles("ADMIN")
//                        .build()
//                );
//        return userDetailsManager;
//    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomLogoutHandler customLogoutHandler(){
        return new CustomLogoutHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }

}
