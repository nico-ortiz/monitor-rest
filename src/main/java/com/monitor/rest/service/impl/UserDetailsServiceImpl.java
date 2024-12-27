package com.monitor.rest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monitor.rest.dto.auth.AuthLoginRequest;
import com.monitor.rest.dto.auth.AuthResponse;
import com.monitor.rest.dto.user.UserRequest;
import com.monitor.rest.exception.NotFoundException;
import com.monitor.rest.model.Role;
import com.monitor.rest.model.User;
import com.monitor.rest.repository.RoleRepository;
import com.monitor.rest.repository.UserRepository;
import com.monitor.rest.utils.JwtUtils;
import com.monitor.rest.validator.ObjectsValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final ObjectsValidator validator;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Usuario con email=" + email + " no encontrado."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        
        user.getRoles()
            .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name()))));
        
        user.getRoles().stream()
            .flatMap(role -> role.getPermission().stream())
            .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        
        return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.isEnabled(),
                    user.isAccountNoExpired(),
                    user.isCredentialNoExpired(),
                    user.isAccountNoLocked(),
                    authorityList);
    }

    public AuthResponse createUser(UserRequest request) {
        validator.validate(request);

        String email = request.getEmail();
        String password = request.getPassword();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        
        if (optionalUser.isPresent()) {
            return new AuthResponse(email, "", false);
        }

        List<String> roleRequest = request.getAuthCreateRoleRequest().getRoleListName();
        
        Set<Role> rolesSet = roleRepository.findRolesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

        if (rolesSet.isEmpty()) {
            throw new IllegalArgumentException("Los roles especificados no existen");
        }
        
        User user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(email)
            .password(passwordEncoder.encode(password))
            .roles(rolesSet)
            .isEnabled(true)
            .accountNoLocked(true)
            .accountNoExpired(true)
            .credentialNoExpired(true)
            .build();
        
        User userCreated = userRepository.save(user);
        List<SimpleGrantedAuthority> authoriyList = new ArrayList<>();

        userCreated.getRoles().forEach(role -> {
            authoriyList.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().name()));
        });

        userCreated.getRoles()
            .stream()
            .flatMap(role -> role.getPermission().stream())
            .forEach(permission -> authoriyList.add(new SimpleGrantedAuthority(permission.getName()))
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getEmail(), user.getPassword(), authoriyList);        
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponse authResponse = new AuthResponse(userCreated.getEmail(), accessToken, true);
        return authResponse;
    }
    
    public AuthResponse login(AuthLoginRequest request) {
        validator.validate(request);

        String email = request.getEmail();
        String password = request.getPassword();

        try {
            Authentication authentication = authenticate(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
    
            String accessToken = jwtUtils.createToken(authentication);
    
            return new AuthResponse(email, accessToken, true);
        } catch (BadCredentialsException exception) {
            return new AuthResponse(email, "", false);
        }
    }

    public Authentication authenticate(String email, String password) {
        UserDetails user = loadUserByUsername(email);

        if (passwordEncoder.matches(password, passwordEncoder.encode(user.getPassword()))) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(email, user.getPassword(), user.getAuthorities());
    }
}
