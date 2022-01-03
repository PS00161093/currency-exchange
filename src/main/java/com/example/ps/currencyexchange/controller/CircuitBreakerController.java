package com.example.ps.currencyexchange.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/circuit-breaker")
    @Retry(name = "dummy-api", fallbackMethod = "dummyApiFallback")
    public String ping() {
        logger.info("Calling dummy-service.");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8989/dummy", String.class);
        return forEntity.getBody();
    }

    public String dummyApiFallback(Exception ex) {
        return "Response from dummyApiFallback!";
    }

    @GetMapping("/v2/circuit-breaker")
    @CircuitBreaker(name = "default", fallbackMethod = "dummyApiFallback")
    @RateLimiter(name = "default") //num of calls per second
    @Bulkhead(name = "default") // allowed num of concurrent calls
    public String pingV2() {
        logger.info("Calling dummy-service.");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8989/dummy", String.class);
        return forEntity.getBody();
    }

}
