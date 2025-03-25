package com.springtraining.invoice.externalService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpTimeoutException;

@Service
@RequiredArgsConstructor
public class ExternalServiceImpl {

    private final ExternalServiceClient externalServiceClient;

    @Retry(name = "externalService", fallbackMethod = "fallback")
    public String simulationDelay(String delay){
        externalServiceClient.simulationDelay(delay);
        throw new RuntimeException("Timeout simulato");
    }

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallback")
    public String simulationStatus(String code){
        return externalServiceClient.simulationStatus(code);
    }

    public String fallback(Throwable ex) {
        return "Il servizio fatturazione non è disponibile. Riprova più tardi.";
    }
}
