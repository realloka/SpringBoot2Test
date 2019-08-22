package com.plantynet.tech2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@SuppressWarnings("deprecation")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        
        http
            .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                //.antMatchers("/**").hasRole("USER")
                //.anyRequest().authenticated()
                .antMatchers("/**").authenticated()
            .and()
                .formLogin()
                /*.loginPage("/login.do")
                .defaultSuccessUrl("/test/")
                .loginProcessingUrl("/loginAction.do")
                .permitAll()*/
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSSEIONID")
                .invalidateHttpSession(true)
            .and()
                .addFilterBefore(filter, CsrfFilter.class)//무슨 역할인지 모르겠음
                .csrf().disable()
            .sessionManagement()//중복 로그인 방지
                .maximumSessions(1)//최대 1명
                .maxSessionsPreventsLogin(false);//true : 두번째 로그인 시도 시 첫번째 로그인 유지, false : 첫번째 로그 아웃됨
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() 
    {
        //출처: https://java.ihoney.pe.kr/498 [허니몬(Honeymon)의 자바guru]
        //deprecated되었네...
        DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new MessageDigestPasswordEncoder("SHA-1"));
        
        return delegatingPasswordEncoder;
        
        //기본으로 아래처럼 등록하면 DB에 넣을 때도 맞게 변환해서 넣어야 함(pdf 참조)
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    /*
    //테스트 계정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        //http://meaownworld.tistory.com/129 There is no PasswordEncoder mapped for the id "null" -> {noop} 비번 앞에 붙여서 해결
        //사용자 저장소
        auth.inMemoryAuthentication()
            .withUser("user").password("{noop}password").roles("USER")
            .and()
            .withUser("admin").password("{noop}password").roles("ADMIN");
    }
    */
}
