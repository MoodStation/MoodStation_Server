package com.moodstation.springboot.service;

import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.enums.RoleType;
import com.moodstation.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(email);
        List<GrantedAuthority> roles = new ArrayList<>();
        if (user == null) {
            throw new UsernameNotFoundException("User not found with Email: " + email);
        }

        if (user.getRole() == RoleType.USER) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if(user.getRole() == RoleType.ADMIN) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if(user.getRole() == RoleType.GUEST) {
            roles.add(new SimpleGrantedAuthority("ROLE_GUEST"));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }
}
