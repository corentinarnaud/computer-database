package com.excilys.arnaud.springConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }
  

  @Bean
  public DigestAuthenticationEntryPoint digestEntryPoint ()
  {
    DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
    digestAuthenticationEntryPoint.setKey("mykey");
    digestAuthenticationEntryPoint.setRealmName("Digest WF Realm");
    return digestAuthenticationEntryPoint;
  }
  
  @Bean
  public DigestAuthenticationFilter digestAuthenticationFilter (
      DigestAuthenticationEntryPoint digestAuthenticationEntryPoint)
  {
    DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
    digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
    digestAuthenticationFilter.setUserDetailsService(userDetailsService());
    digestAuthenticationFilter.setPasswordAlreadyEncoded(true);
    return digestAuthenticationFilter;
  }
  
  

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/computer")
    .ignoringAntMatchers("/computer/**")
    /*.and().addFilter(digestAuthenticationFilter(digestEntryPoint ()))
    .exceptionHandling().authenticationEntryPoint(digestEntryPoint ())*/
    .and()
    .authorizeRequests()
    .antMatchers("/css/**").permitAll()
    .antMatchers("/fonts/**").permitAll()
        .antMatchers("/i18/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/dashboard").permitAll()
        .antMatchers("/registration").permitAll()
        .antMatchers("/computer").permitAll()
        .antMatchers("/computer/**").permitAll()
        .antMatchers("/company").permitAll()
        .anyRequest().authenticated()
        .and().formLogin().loginPage("/login").permitAll()
        .and().logout().permitAll();
  }
}
