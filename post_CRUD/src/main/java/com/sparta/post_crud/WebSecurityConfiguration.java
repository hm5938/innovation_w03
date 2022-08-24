package com.sparta.post_crud;

import com.sparta.post_crud.security.filter.CustomAuthenticationFilter;
import com.sparta.post_crud.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
    private final TokenProvider tokenProvider;


    public WebSecurityConfiguration(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // resources 모든 접근을 허용하는 설정을 해버리면
        // HttpSecurity 설정한 ADIM권한을 가진 사용자만 resources 접근가능한 설정을 무시해버린다.
//        web.ignoring()
//                .antMatchers("/resources/**");
    }

        @Override
        public void configure(HttpSecurity http) throws Exception {
//            http.cors().configurationSource(corsConfigurationSource());
            http.csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            // JWT등의 토큰 방식을 사용할 때 설정 - 스프링시큐리티가 세션을 생성하지 않을 수 있도록
//            http.formLogin()
//                    .loginPage("/login-page")
//                    .loginProcessingUrl("/login-process")
//                    .defaultSuccessUrl("/main")
//                    .successHandler(new CustomAuthenticationSuccessHandler())
//                    .failureUrl("login-fail")
//                    .failureHandler(new CustomAuthenticationFailureHandler());
            //  로그인 성공, 실패 처리를 할 때 설정하는 법
            http.authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/users/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/posts/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/reviews/**").permitAll()
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated();

            // 접근 권한 설정
            http.addFilterBefore(new CustomAuthenticationFilter(tokenProvider),
                    UsernamePasswordAuthenticationFilter.class);
            //addFilterBefore 는 UsernamePasswordAuthenticationFilter 전에 동작
            //addFilterAfter 는 UsernamePasswordAuthenticationFilter 실행 후 동작

        }
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}