package com.magadiflo.reactive.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/greetings")
public class HelloController {
    @GetMapping(path = "/hello")
    public Mono<String> hello() {
        return Mono.just("Hola Mundo!");
    }
}