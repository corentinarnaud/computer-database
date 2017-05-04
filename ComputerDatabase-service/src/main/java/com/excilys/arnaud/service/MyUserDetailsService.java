package com.excilys.arnaud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.arnaud.model.User;
import com.excilys.arnaud.persistence.UserDAO;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
  
    @Autowired
    private UserDAO userRepository;
     
    public UserDetails loadUserByUsername(String name)
      throws UsernameNotFoundException {
  
        Optional<User> user = userRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(
              "No user found with username: "+ name);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return  new org.springframework.security.core.userdetails.User
          (user.get().getName(), 
          user.get().getPassword().toLowerCase(), enabled, accountNonExpired, 
          credentialsNonExpired, accountNonLocked, 
          getAuthorities(user.get().getRoles()));
    }
     
    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}