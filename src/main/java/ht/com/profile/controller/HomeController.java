package ht.com.profile.controller;

import ht.com.profile.model.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping({"home"})
    public Profile index() {
        return new Profile("home", "Home", "03123123123", "other info of user");
    }
}
