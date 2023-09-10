package com.example.shopAPI.auth.service;

import com.example.shopAPI.auth.interfaces.UserService;
import com.example.shopAPI.auth.model.AuthUser;
import com.example.shopAPI.auth.model.Role;
import com.example.shopAPI.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    private List<GrantedAuthority> generateGrantedAuthorities(AuthUser user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(user.getRole() == null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.name()));
        } else {
            Role role = user.getRole();
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return grantedAuthorities;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) {
                AuthUser authUser = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                List<GrantedAuthority> grantedAuthorities = generateGrantedAuthorities(authUser);
                return new User(authUser.getEmail(), authUser.getPassword(),
                        true, true, true, true, grantedAuthorities);
            }
        };
    }
}
