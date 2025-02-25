package com.springtraining.invoice.service;

import com.springtraining.invoice.model.Invoice;
import com.springtraining.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("OrderService v1.0")
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    @Override
    public Invoice save(Invoice invoice) {
        invoice.setInvoiceDate(new Date());
        invoiceRepository.save(invoice);
        return invoice;
    }
}
