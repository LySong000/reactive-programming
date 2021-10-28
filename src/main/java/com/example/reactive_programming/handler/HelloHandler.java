package com.example.reactive_programming.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class HelloHandler {

    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Hello Reacotive Programming"), String.class);
    }

    public Mono<ServerResponse> helloSecond(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(
                Flux.interval(Duration.ofSeconds(1)).parallel().runOn(Schedulers.parallel())
                        .map(item -> "Hello Reacotive Programming" + item), String.class);
    }
}
