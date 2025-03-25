package com.springtraining.invoice.externalService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfigExternlalService {
    @Bean
    public ExternalServiceClient externalServiceClient() {
        WebClient webClient = WebClient.create("https://httpbin.org");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(WebClientAdapter.create(webClient))
                .build();
        return factory.createClient(ExternalServiceClient.class);
    }
}
