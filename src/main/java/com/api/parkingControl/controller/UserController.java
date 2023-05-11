package com.api.parkingControl.controller;

import com.api.parkingControl.model.User;
import com.api.parkingControl.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMongoRepository repository;

    @PostMapping
    User save() {
        User user = User.builder().name("Franzim").age(40).id("2").build();
        return repository.save(user);
    }

    @GetMapping
    User getByName(String name) {
        return repository.findByName(name);
    }
}
