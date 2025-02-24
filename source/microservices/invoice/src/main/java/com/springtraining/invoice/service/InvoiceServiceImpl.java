package com.springtraining.invoice.service;

import com.springtraining.invoice.model.Invoice;
import com.springtraining.invoice.repository.InvoiceRepository;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Date;

@Service("OrderService v1.0")
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    @Override
    public Invoice save(Invoice invoice) {
        invoiceRepository.save(invoice);
        return invoice;
    }
}
