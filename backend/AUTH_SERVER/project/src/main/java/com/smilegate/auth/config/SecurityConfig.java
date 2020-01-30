package com.smilegate.auth.config;

import com.smilegate.auth.filters.JwtAuthenticationFilter;
import com.smilegate.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(
                            "/users/signin",
                            "/users/signup/**",
                            "/users/password/**",
                            "/exception/**"
                    ).permitAll()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                    .addFilterBefore(
                            new JwtAuthenticationFilter(jwtUtil),
                            UsernamePasswordAuthenticationFilter.class
                    );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
