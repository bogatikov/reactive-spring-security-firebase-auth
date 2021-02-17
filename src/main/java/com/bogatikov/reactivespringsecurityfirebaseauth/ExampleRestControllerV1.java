package com.bogatikov.reactivespringsecurityfirebaseauth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/expample")
public class ExampleRestControllerV1 {

    @GetMapping("/need/authenticate")
    public Mono<String> needAuthentication() {
        return Mono.just("Hello, you're authenticated");
    }

    @GetMapping("/need/authority")
    @PreAuthorize("hasAuthority('FEED_VIEW')")
    public Mono<String> needAuthority() {
        return Mono.just("Hello, you're authenticated and have specific authority");
    }
}
