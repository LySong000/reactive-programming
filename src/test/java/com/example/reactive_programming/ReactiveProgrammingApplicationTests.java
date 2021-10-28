package com.example.reactive_programming;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@SpringBootTest
class ReactiveProgrammingApplicationTests {

    @Test
    void contextLoads() {
        Flux.create(fluxSink -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fluxSink.next("Hello");
            }
            fluxSink.complete();
        })
//                .buffer(10).onBackpressureBuffer(3)
                .subscribe(item -> {
                    try {
                        System.out.println("new message comming");
                        Thread.sleep(3000);
                        System.out.println(item);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void test_generate_publish_on() throws InterruptedException {
        Flux<Object> characterFlux = Flux.generate(sink -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            sink.next("hello");
        }).publishOn(Schedulers.parallel());
        System.out.println("hi after create");
        characterFlux.log().subscribe(x -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(x);
        });
        System.out.println("hello~");
    }

    @Test
    void test_generate_subscribe_on() throws InterruptedException {
        Flux<Object> characterFlux = Flux.generate(sink -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            sink.next("hello");
        });
        System.out.println("hi after create");
        characterFlux.log().subscribe(x -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(x);
        });
        Thread.sleep(10);
        System.out.println("hello~");
    }

    @Test
    void test_delay() throws InterruptedException {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa")
                .delaySubscription(Duration.ofMillis(500));
        characterFlux.log().subscribe(x -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(x);
        });
        System.out.println("hello~");
    }

    @Test
    void test_run_on(){
        ParallelFlux<Object> objectParallelFlux = Flux.create(this::initNumber).parallel().runOn(Schedulers.parallel());
        objectParallelFlux.subscribe(System.out::println);
    }

    @Test
    void test_publish_on_create() {
        Flux<Object> objectFlux = Flux.create(this::initNumber).publishOn(Schedulers.parallel());
        objectFlux.subscribe(System.out::println);
    }

    void initNumber(FluxSink<Object> fluxSink) {
        for (int i = 0; i < 100; i++) {
            fluxSink.next(i);
        }
        fluxSink.complete();
    }
}
