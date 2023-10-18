package com.magadiflo.reactive.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/v1/greetings")
public class HelloController {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(path = "/hello")
    public Mono<ResponseEntity<Message>> hello(Mono<Authentication> authenticationMono) {
        return authenticationMono
                .map(Principal::getName)
                .map(name -> new Message("Hola, " + name))
                .map(ResponseEntity::ok);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(path = "/ciao")
    public Mono<ResponseEntity<Message>> ciao() {
        return Mono.just(ResponseEntity.ok(new Message("¡Adiós!")));
    }

}