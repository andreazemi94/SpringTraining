package com.springtraining.loginjwt.service.impl;

import com.springtraining.loginjwt.model.Role;
import com.springtraining.loginjwt.model.RolesType;
import com.springtraining.loginjwt.model.User;
import com.springtraining.loginjwt.repository.RolesRepository;
import com.springtraining.loginjwt.repository.UserRepository;
import com.springtraining.loginjwt.service.RolesService;
import com.springtraining.loginjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesService rolesService;

    @Override
    public Optional<User> create(String username, String password, String roles) {
        Set<Role> setRoles = Arrays.stream(roles.split(","))
                .map(role->rolesService.findByName(RolesType.valueOf(role)))
                .map(Optional::orElseThrow)
                .collect(Collectors.toSet());
        return Optional.of(userRepository.save(User.builder().username(username).password(passwordEncoder.encode(password)).roles(setRoles).build()));
    }
}
