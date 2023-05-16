package com.anastas.carsshop.service;

import com.anastas.carsshop.excaption.ElementNotFoundException;
import com.anastas.carsshop.model.Authority;
import com.anastas.carsshop.model.User;
import com.anastas.carsshop.repository.AuthorityRepository;
import com.anastas.carsshop.repository.UserRepository;
import com.anastas.carsshop.service.interfaces.AuthorityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
@Secured(value = "ROLE_ADMIN")
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository, UserRepository userRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User setAuthorityToUser(String username, String authorityName) {
        if (null == username || username.isBlank()){
            throw new IllegalArgumentException("Username cannot be blank!");
        }
        if (null == authorityName || authorityName.isBlank() || !authorityName.startsWith("ROLE_")){
            throw new IllegalArgumentException("Authority must no be blank and should start with \"ROLE_\"");
        }

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new ElementNotFoundException("User with username = " + username + " not found!");
        }

        Optional<Authority> authority = authorityRepository.findByAuthority(authorityName);
        if (authority.isPresent() && Objects.equals(authority.get().getUser().getId(), user.get().getId())){
            throw new IllegalArgumentException("User with username = " + username +
                    " has already had authority with name = " + authorityName);
        }

        authorityRepository.save(new Authority(null, authorityName.toUpperCase(), user.get()));
        Optional<User> savedUser = userRepository.findByUsername(username);
        if (savedUser.isEmpty()){
            throw new ElementNotFoundException("User with username = " + username + " not found!");
        }

        return savedUser.get();
    }
}
