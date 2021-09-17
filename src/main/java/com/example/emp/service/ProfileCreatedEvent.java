package com.example.emp.service;

import com.example.emp.model.Profile;
import org.springframework.context.ApplicationEvent;

public class ProfileCreatedEvent extends ApplicationEvent {

    public ProfileCreatedEvent(Profile profile) {
        super(profile);
    }
}
