package com.anastas.carsshop.controller;

import com.anastas.carsshop.dto.AuthResponse;
import com.anastas.carsshop.dto.UserPrincipal;
import com.anastas.carsshop.helper.ChangePasswordHelper;
import com.anastas.carsshop.helper.UserModelBasic;
import com.anastas.carsshop.model.User;
import com.anastas.carsshop.security.CustomUserDetailsService;
import com.anastas.carsshop.service.interfaces.UserService;
import com.anastas.carsshop.util.JwtTokenUtil;
import com.anastas.carsshop.excaption.BadRequestContentException;
import com.anastas.carsshop.excaption.ElementNotFoundException;
import com.anastas.carsshop.helper.ChangeUserDataHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, CustomUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<AuthResponse> login(@RequestBody @Valid UserModelBasic userModelBasic, Errors errors){
        if (errors.hasErrors()) {
            if (null != errors.getFieldError()) {
                throw new BadRequestContentException(errors.getFieldError().getDefaultMessage());
            } else {
                throw new BadRequestContentException();
            }
        }

        authenticate(userModelBasic.getUsername(), userModelBasic.getPassword());
        final UserPrincipal userDetails = (UserPrincipal) userDetailsService
                .loadUserByUsername(userModelBasic.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(
                userDetails.getUsername(),
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                token
        ));
     }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Valid UserModelBasic userModelBasic, Errors errors) {
        if (errors.hasErrors()) {
            if (null != errors.getFieldError()) {
                throw new BadRequestContentException(errors.getFieldError().getDefaultMessage());
            } else {
                throw new BadRequestContentException();
            }
        }

        User user = new User();
        user.setUsername(userModelBasic.getUsername());
        user.setPassword(userModelBasic.getPassword());
        user.setAuthority(userModelBasic.getAuthority());
        return userService.register(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    public User findByUsername(@RequestParam String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new ElementNotFoundException("User with username = " + username + " not found!"));
    }

    @GetMapping(path = "/info")
    @ResponseStatus(HttpStatus.OK)
    public User findUserInfo(@RequestParam String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new ElementNotFoundException("User with username = " +
                        SecurityContextHolder.getContext().getAuthentication().getName() + " not found!"));
    }

    @PatchMapping(path = "/password")
    @ResponseStatus(HttpStatus.CREATED)
    public User changePassword(@RequestBody @Valid ChangePasswordHelper passwordHelper, Errors errors) {
        if (errors.hasErrors()) {
            if (null != errors.getFieldError()) {
                throw new BadRequestContentException(errors.getFieldError().getDefaultMessage());
            } else {
                throw new BadRequestContentException();
            }
        }

        return userService.changePassword(passwordHelper);
    }
    @PatchMapping(path = "/data")
    @ResponseStatus(HttpStatus.CREATED)
    public User changeUserData(@RequestBody @Valid ChangeUserDataHelper dataHelper, @RequestParam String username,
                               Errors errors) {
        if (errors.hasErrors()) {
            if (null != errors.getFieldError()) {
                throw new BadRequestContentException(errors.getFieldError().getDefaultMessage());
            } else {
                throw new BadRequestContentException();
            }
        }

        return userService.changeUserData(dataHelper, username);
    }
}
