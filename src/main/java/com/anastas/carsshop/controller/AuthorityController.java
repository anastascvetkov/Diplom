package com.anastas.carsshop.controller;

import com.anastas.carsshop.model.User;
import com.anastas.carsshop.service.interfaces.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/authority")
@PreAuthorize("hasRole('ADMIN')")
public class AuthorityController {

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User setAuthorityToUser(@RequestParam String username, @RequestParam String authority){
        return authorityService.setAuthorityToUser(username, authority);
    }
}
