package com.springtraining.invoice.repository;

import com.springtraining.invoice.model.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice,Long> { }
