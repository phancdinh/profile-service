package ht.com.profile.controller;

import ht.com.profile.model.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/profile")
public class ProfileController {

    @GetMapping(value = "/{id}")
    public Profile findOne(@PathVariable String id) {
        return new Profile(id, "Dinh Phan", "0123456778", "other info of user");
    }

}
