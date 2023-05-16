package com.anastas.carsshop.helper;

import com.anastas.carsshop.model.Authority;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class UserModelBasic {

    @NotBlank(message = "Username is mandatory and cannot be blank!")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Password is mandatory and cannot be blank!")
    @Length(min = 8, message = "Password length must be at least 8 symbols")
    @Column(name = "password", nullable = false)
    private String password;

    private Set<Authority> authority;
}
