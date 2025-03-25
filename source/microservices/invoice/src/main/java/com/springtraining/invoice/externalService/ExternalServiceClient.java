package com.springtraining.invoice.externalService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface ExternalServiceClient {

    @GetExchange("/delay/{delay}")
    String simulationDelay(@PathVariable String delay);

    @GetExchange("/status/{code}")
    String simulationStatus(@PathVariable String code);
}
