package com.example.emp.service;

import com.example.emp.model.Profile;
import com.example.emp.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@Slf4j
@DataMongoTest
@Import(ProfileService.class)
class ProfileServiceTest {
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    ProfileServiceTest(ProfileService profileService, ProfileRepository profileRepository) {
        this.profileService = profileService;
        this.profileRepository = profileRepository;
    }

    @Test
    public void getAll(){
        Flux<Profile> savedProfile = profileRepository.saveAll(Flux.just(new Profile(null, "Ade"),
                new Profile("2", "Jade")));
        Flux<Profile> composite = profileService.all().thenMany(savedProfile);
        Predicate<Profile> match = profile -> savedProfile.any(saveItem -> saveItem.equals(profile)).block();
        StepVerifier
                .create(composite)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }
}