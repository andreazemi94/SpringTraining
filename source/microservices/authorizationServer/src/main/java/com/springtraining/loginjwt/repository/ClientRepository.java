package com.springtraining.loginjwt.repository;

import com.springtraining.loginjwt.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client,Long> { }
