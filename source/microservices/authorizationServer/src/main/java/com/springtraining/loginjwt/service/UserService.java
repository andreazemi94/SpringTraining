package com.springtraining.loginjwt.service;

import com.springtraining.loginjwt.model.Role;
import com.springtraining.loginjwt.model.RolesType;
import com.springtraining.loginjwt.model.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> create (String username, String roles);
}
