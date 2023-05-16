package com.anastas.carsshop.service.interfaces;


import com.anastas.carsshop.model.User;

public interface AuthorityService {

    User setAuthorityToUser(String username, String authority);
}
