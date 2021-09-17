package com.example.emp.service;

import com.example.emp.model.Profile;
import com.example.emp.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProfileService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ProfileRepository profileRepository;

    public ProfileService(ApplicationEventPublisher applicationEventPublisher, ProfileRepository profileRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.profileRepository = profileRepository;
    }

    public Flux<Profile> all() {
       return this.profileRepository.findAll();
    }

    public Mono<Profile> get(String id){
        return this.profileRepository.findById(id);
    }

    public Mono<Profile> update(String id, String email){
        return this.profileRepository.findById(id)
                .map(profile -> new Profile(profile.getId(), email))
                .flatMap(profile -> this.profileRepository.save(profile));
    }

    public Mono<Profile> delete(String id){
        return this.profileRepository.findById(id)
                .flatMap(profile -> this.profileRepository.deleteById(id).thenReturn(profile));
    }

    public Mono<Profile> create(String email) {
        return this.profileRepository
                .save(new Profile(null, email))
                .doOnSuccess(p -> this.applicationEventPublisher.publishEvent(new ProfileCreatedEvent(p)));
    }
}
