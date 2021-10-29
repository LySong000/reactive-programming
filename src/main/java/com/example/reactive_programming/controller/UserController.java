package com.example.reactive_programming.controller;

import com.example.reactive_programming.entity.User;
import com.example.reactive_programming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public Flux<Object> test(){
        Flux<Object> characterFlux = Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName());
                sink.next("hello");
            }
            sink.complete();
        }).subscribeOn(Schedulers.parallel());
        runIt(characterFlux);
        System.out.println("hi after create");
        System.out.println("have subscribe");
        return Flux.just("aaaa");
    }

    @PostMapping
    public Mono<User> save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable("username") String username) {
        return userService.deleteByUsername(username);
    }

    @GetMapping
    public Flux<User> findAll() {
        return userService.findAll();
    }

    public void runIt(Flux<Object> characterFlux) {
        characterFlux.log().subscribe(x -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(x);
        });
    }
}
