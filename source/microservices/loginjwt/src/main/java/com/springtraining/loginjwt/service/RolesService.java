package com.springtraining.loginjwt.service;


import com.springtraining.loginjwt.model.Role;
import com.springtraining.loginjwt.model.RolesType;

import java.util.Optional;

public interface RolesService {
    Optional<Role> findByName(RolesType name);
}
