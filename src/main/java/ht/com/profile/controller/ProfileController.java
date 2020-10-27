package ht.com.profile.controller;

import ht.com.profile.model.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

    @GetMapping(value = "/{id}")
    public Profile findOne(@PathVariable Long id) {
        return new Profile("Dinh Phan", "0123456778", "other info of user");
    }

}
