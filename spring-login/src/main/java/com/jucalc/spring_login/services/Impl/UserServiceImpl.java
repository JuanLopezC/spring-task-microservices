package com.jucalc.spring_login.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jucalc.spring_login.repository.UserRepository;
import com.jucalc.spring_login.services.UserService;

@Service
public class UserServiceImpl implements UserService {
  
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
          .orElseThrow(() -> new UsernameNotFoundException("User Not found"));
      }
    };
  }
}
