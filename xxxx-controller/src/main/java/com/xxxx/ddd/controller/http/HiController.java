package com.xxxx.ddd.controller.http;


import com.xxxx.ddd.application.service.event.EventAppService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;

@RestController
@RequestMapping("/hello")
public class HiController {
    @Autowired
    private EventAppService eventAppService;
    @Autowired
    private RestTemplate restTemplate;

    @RateLimiter(name = "backendA", fallbackMethod = "fallbackHello")
    @GetMapping("/hi")
    public String hello() {
        return eventAppService.sayHi("Hi");
    }

    public String fallbackHello(Throwable throwable) {
        return "too many request";
    }

    @GetMapping("/hihi")
    @RateLimiter(name = "backendB", fallbackMethod = "fallbackHello")
    public String hi() {
        return eventAppService.sayHi("dinhpham04");
    }

    private final SecureRandom secureRandom = new SecureRandom();
    @GetMapping("/circuit/breaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackGetProduct")
    public String circuitBreaker() {
        int productId = secureRandom.nextInt(20) + 1;
        String url = "https://fakestoreapi.com/products/" + productId;
        return restTemplate.getForObject(url, String.class);
    }

    public String fallbackGetProduct(Throwable throwable) {
        return "falure when get product by id";
    }
}
