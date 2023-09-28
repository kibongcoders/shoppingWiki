package com.kibong.shoppingwiki;

import com.kibong.shoppingwiki.security.AuthenticationFilter;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import com.kibong.shoppingwiki.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final ObjectPostProcessor<Object> objectPostProcessor;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Environment env;

    private static final String[] PERMIT_LIST = {
            "/users/**",
            "/**"
    };

    private static final String[] AUTHOR_LIST = {
            "/users/getUser",
    };

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(
                                new MvcRequestMatcher(introspector, "/**")).permitAll()
                                .anyRequest().authenticated()


                ).addFilter(getAuthenticationFilter())
                .httpBasic(Customizer.withDefaults())
                .formLogin(withDefaults()); // 기본 로그인 페이지 사용

        return http.build();
    }

    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        return auth.build();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userRepository,userService ,env);
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
        authenticationFilter.setAuthenticationManager(authenticationManager(builder));
        return authenticationFilter;
    }
}
