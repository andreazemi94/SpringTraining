package com.springtraining.invoice.externalService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpTimeoutException;

@Service
@RequiredArgsConstructor
public class ExternalServiceImpl {

    private final ExternalServiceClient externalServiceClient;

    @Retry(name = "externalService", fallbackMethod = "fallback")
    @TimeLimiter(name = "externalService", fallbackMethod = "fallbackTimeLimiter")
    public String simulationDelay(String delay){
        return externalServiceClient.simulationDelay(delay);
    }

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallback")
    @RateLimiter(name = "externalService", fallbackMethod = "fallbackRateLimit")
    public String simulationStatus(String code){
        return externalServiceClient.simulationStatus(code);
    }

    public String fallback(Throwable ex) {
        return "Il servizio esterno non è disponibile. Riprova più tardi.";
    }

    public String fallbackRateLimit(Throwable ex) {
        return "Troppe richieste! Riprova più tardi.";
    }

    public String fallbackTimeLimiter(Throwable t) {
        return "Operazione non completata in tempo, si prega di riprovare più tardi.";
    }
}
