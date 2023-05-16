package com.anastas.carsshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicDto {


    @NotBlank(message = "Username is mandatory and cannot be blank!")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Password is mandatory and cannot be blank!")
    @Length(min = 8, message = "Password length must be at least 8 symbols")
    @Column(name = "password", nullable = false)
    private String password;
}
