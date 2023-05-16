package com.anastas.carsshop.security;

import com.anastas.carsshop.dto.AuthorityDTO;
import com.anastas.carsshop.dto.UserPrincipal;
import com.anastas.carsshop.model.User;
import com.anastas.carsshop.repository.UserRepository;
import com.anastas.carsshop.security.mapper.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        List<AuthorityDTO> authorities = user.get().getAuthority()
                .stream()
                .map(AuthorityMapper::toAuthorityDTO)
                .collect(Collectors.toList());
        return new UserPrincipal(user.get().getUsername(), user.get().getPassword(), authorities);
    }

    public List<UserDetails> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDetails> mappedUsers = new ArrayList<>();

        users.stream()
                .forEach(u -> mappedUsers.add(new UserPrincipal(u.getUsername(), null,
                        u.getAuthority().stream()
                                .map(AuthorityMapper::toAuthorityDTO)
                                .collect(Collectors.toList()))));

        return mappedUsers;
    }
}
