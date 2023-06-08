package com.robinsingh.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }

}
