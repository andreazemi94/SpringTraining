package com.springtraining.loginjwt.service.impl;

import com.springtraining.loginjwt.model.Role;
import com.springtraining.loginjwt.model.RolesType;
import com.springtraining.loginjwt.repository.RolesRepository;
import com.springtraining.loginjwt.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;

    @Override
    public Optional<Role> findByName(RolesType name) {
        return StreamSupport.stream(rolesRepository.findAll().spliterator(), false).filter(role->role.getName() == name).findAny();
    }
}
