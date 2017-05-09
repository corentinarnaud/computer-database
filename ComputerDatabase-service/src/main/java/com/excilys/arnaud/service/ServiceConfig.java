package com.excilys.arnaud.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;



@Configuration
@ComponentScan(basePackages = "com.excilys.arnaud")
//@Import(value = {PersistenseConfig.class})
public class ServiceConfig {

  @Bean
  public Md5PasswordEncoder passwordEncoder() {
      return new Md5PasswordEncoder();
  }
  
}
