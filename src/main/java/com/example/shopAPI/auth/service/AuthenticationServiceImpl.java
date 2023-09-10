package com.example.shopAPI.auth.service;

import com.example.shopAPI.auth.dao.JwtAuthenticationResponse;
import com.example.shopAPI.auth.dao.SignInRequest;
import com.example.shopAPI.auth.dao.SignUpRequest;
import com.example.shopAPI.auth.interfaces.AuthenticationService;
import com.example.shopAPI.auth.interfaces.JwtService;
import com.example.shopAPI.auth.model.AuthUser;
import com.example.shopAPI.auth.model.Role;
import com.example.shopAPI.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var authUser = new AuthUser(request.getEmail(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(authUser);
        var grantedAuthorities = generateGrantedAuthorities(authUser);
        var user = new User(request.getEmail(), request.getPassword(),
                true, true, true, true, grantedAuthorities);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var authUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var grantedAuthorities = generateGrantedAuthorities(authUser);
        var user = new User(request.getEmail(), request.getPassword(),
                true, true, true, true, grantedAuthorities);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

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
}
