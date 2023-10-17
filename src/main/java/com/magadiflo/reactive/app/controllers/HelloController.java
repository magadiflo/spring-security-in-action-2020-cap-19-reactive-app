package com.magadiflo.reactive.app.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/greetings")
public class HelloController {

    @GetMapping(path = "/hello")
    public Mono<String> hello(Mono<Authentication> authenticationMono) {
        return authenticationMono.map(authentication -> "Hola, " + authentication.getName());
    }

    @GetMapping(path = "/ciao")
    public Mono<String> ciao() {
        return Mono.just("Chao!");
    }

}