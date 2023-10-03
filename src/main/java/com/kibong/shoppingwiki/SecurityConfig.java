package com.kibong.shoppingwiki;

import com.kibong.shoppingwiki.security.AuthenticationFilter;
import com.kibong.shoppingwiki.security.JwtAuthTokenProvider;
import com.kibong.shoppingwiki.security.JwtFilter;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    JwtAuthTokenProvider jwtAuthTokenProvider;

    private static final String[] PERMIT_LIST = {
            "/users/**",
            "/**"
    };

    private static final String[] AUTHOR_LIST = {
            "/users/getUser",
    };

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers("/swagger-ui/**").permitAll();
            authorizeRequests.requestMatchers("/v3/**").permitAll();
            authorizeRequests.requestMatchers("/user/**").permitAll();
            authorizeRequests.requestMatchers("/user/getUserInfo/**").authenticated();
            authorizeRequests.requestMatchers("/user/updateUser/**").authenticated();
            authorizeRequests.anyRequest().permitAll();
        });
        http.addFilterBefore(new JwtFilter(jwtAuthTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
            web.ignoring().requestMatchers("/swagger-ui/**", "/shoppingwiki/v3/**", "/user/login");
        };
    }


}
