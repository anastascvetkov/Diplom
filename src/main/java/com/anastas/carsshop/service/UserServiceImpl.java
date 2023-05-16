package com.anastas.carsshop.service;

import com.anastas.carsshop.service.interfaces.UserService;
import com.anastas.carsshop.excaption.ElementNotFoundException;
import com.anastas.carsshop.excaption.IncorrectPasswordException;
import com.anastas.carsshop.helper.ChangePasswordHelper;
import com.anastas.carsshop.helper.ChangeUserDataHelper;
import com.anastas.carsshop.model.Authority;
import com.anastas.carsshop.model.User;
import com.anastas.carsshop.repository.AuthorityRepository;
import com.anastas.carsshop.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Lock(LockModeType.WRITE)
    public synchronized User register(User user) {
        if (null == user){
            log.warn("User must not be null on registration!");
            throw new IllegalArgumentException("User cannot be null!");
        }

        if (userRepository.existsByUsername(user.getUsername())){
            log.warn("User with username = " + user.getUsername() + " already exists!");
            throw new IllegalArgumentException("User with username = " + user.getUsername() + " already exists!");
        }



        log.debug("Encode user password");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.debug("Try to save user with username = " + user.getUsername());
        User savedUser = userRepository.save(user);

        Authority authority = new Authority(null,  "ROLE_USER", savedUser);
        authorityRepository.save(authority);
        return savedUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if(null == username || username.isBlank()){
            log.warn("Username cannot be blank!");
            throw new IllegalArgumentException("Username cannot be blank!");
        }

        log.debug(("Try to fetch user fetch username = " + username));
        return userRepository.findByUsername(username);
    }

    @Lock(LockModeType.WRITE)
    @Override
    public User changePassword(ChangePasswordHelper passwordHelper) {
        if (null == passwordHelper){
            log.warn("User credentials for changing password cannot be null!");
            throw new IllegalArgumentException("Data for changing user password cannot be null!");
        }

        log.debug("Try to get user with username = " + passwordHelper.getUsername());
        Optional<User> user =  userRepository.findByUsername(passwordHelper.getUsername());
        if (user.isPresent()) {
            if (!passwordHelper.getNewPassword().equals(passwordHelper.getNewPasswordAgain())){
                log.warn("New password and repeated not match!");
                throw new IllegalArgumentException("New password and repeated not match!");
            }
            if (!passwordEncoder.matches(passwordHelper.getOldPassword(), user.get().getPassword())){
                log.warn("Incorrect password!");
                throw new IncorrectPasswordException();
            }else{
                log.debug("set new password to user = " + user.get().getUsername());
                user.get().setPassword(passwordEncoder.encode(passwordHelper.getNewPassword()));

                user.get().setAuthority(null);
                log.debug("try to save changed password");
                return userRepository.save(user.get());
            }

        }
        log.warn("User with username = " + passwordHelper.getUsername() + " not found");
        throw new ElementNotFoundException("User with username = " + passwordHelper.getUsername() + " not found");
    }

    @Override
    public User changeUserData(ChangeUserDataHelper dataHelper, String username) {
        if (dataHelper == null){
            log.warn("User data cannot be null!");
            throw new IllegalArgumentException("User data cannot be null!");
        }

        if (username.isBlank()){
            log.warn("Username cannot be blank!");
            throw new IllegalArgumentException("Username cannot be blank!");
        }

        Optional<User> user =
                userRepository.findByUsername(username);

        if (user.isPresent()){
            if (dataHelper.getContactName() != null){
                if (dataHelper.getContactName().isBlank()){
                    throw new IllegalArgumentException("Contact name cannot be blank!");
                }
                user.get().setContactName(dataHelper.getContactName());
            }
            if (dataHelper.getAddress() != null){
                if (dataHelper.getAddress().isBlank()){
                    throw new IllegalArgumentException("Address cannot be blank!");
                }
                user.get().setAddress(dataHelper.getAddress());
            }
            if (dataHelper.getPhoneNumber() != null){
                user.get().setPhoneNumber(dataHelper.getPhoneNumber());
            }
            if (dataHelper.getEmail() != null){
                user.get().setEmail(dataHelper.getEmail());
            }
            return userRepository.save(user.get());
        }else {
            throw new ElementNotFoundException("User with username = " +
                    SecurityContextHolder.getContext().getAuthentication().getName() + " not found!");
        }
    }

    @Override
    public Optional<User> findUserForProductCodeOwner(Long productCode) {
        if (productCode == null || productCode < 0){
            throw new IllegalArgumentException("Product code is not valid!");
        }

        List<User> users = userRepository.findAll();

        for (User u : users){
            if(u.getProducts().stream().anyMatch(product -> Objects.equals(product.getId(), productCode))){
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
}
