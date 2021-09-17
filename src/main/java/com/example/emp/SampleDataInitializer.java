package com.example.emp;

import com.example.emp.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Component
@Profile("demo")
public class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final ProfileRepository profileRepository;

    public SampleDataInitializer(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        profileRepository.deleteAll()
                .thenMany(Flux.just("A","B","C","D"))
                .map(name -> new com.example.emp.model.Profile(UUID.randomUUID().toString(), name+"@email.com"))
                .flatMap(map -> profileRepository.save(map))
                .thenMany(profileRepository.findAll())
                .subscribe(profile -> log.info("saving "+profile.toString()));
    }
}
