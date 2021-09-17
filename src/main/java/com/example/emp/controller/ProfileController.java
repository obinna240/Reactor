package com.example.emp.controller;

import com.example.emp.model.Profile;
import com.example.emp.service.ProfileService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping(value="/profiles", produces= MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    private final MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public Publisher<Profile> getAll(){
        return this.profileService.all();
    }

    @GetMapping("/{id}")
    public Publisher<Profile> getById(@PathVariable("id") String id){
        return this.profileService.get(id);
    }

    @PostMapping
    public Publisher<ResponseEntity<Profile>> create(@RequestBody Profile profile){
        return this.profileService.create(profile.getEmail())
                .map(profileCreated -> ResponseEntity.created(URI.create("/profiles"+profileCreated.getId()))
                        .contentType(mediaType).build());
    }

    @DeleteMapping("/{id}")
    public Publisher<Profile> deleteById(@PathVariable String id){
        return this.profileService.delete(id);
    }

    @PutMapping("/{id}")
    public Publisher<ResponseEntity<Profile>> updateById(@PathVariable String id, @RequestBody Profile profile) {
        return Mono
                .just(profile)
                .flatMap(profiles -> this.profileService.update(id, profiles.getEmail()))
                .map(profiles2 -> ResponseEntity.ok().contentType(this.mediaType).build());
    }

}
