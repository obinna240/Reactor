package com.example.emp.repository;

import com.example.emp.model.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
//    Flux<Profile> findByEmail(String email);
}
