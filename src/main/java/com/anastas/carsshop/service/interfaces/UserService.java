package com.anastas.carsshop.service.interfaces;

import com.anastas.carsshop.helper.ChangePasswordHelper;
import com.anastas.carsshop.helper.ChangeUserDataHelper;
import com.anastas.carsshop.model.User;

import java.util.Optional;

public interface UserService{
    User register(User user);

    Optional<User> findByUsername(String username);

    User changePassword(ChangePasswordHelper passwordHelper);

    User changeUserData(ChangeUserDataHelper dataHelper, String username);

    Optional<User> findUserForProductCodeOwner(Long productCode);
}
