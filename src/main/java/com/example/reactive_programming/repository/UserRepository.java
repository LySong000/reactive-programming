package com.example.reactive_programming.repository;

import com.example.reactive_programming.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findUserByUsername(String username);

    Mono<Long> deleteUserByUsername(String username);
}
