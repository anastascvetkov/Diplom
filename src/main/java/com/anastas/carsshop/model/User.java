package com.anastas.carsshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory and cannot be blank!")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @NotBlank(message = "Password is mandatory and cannot be blank!")
    @Length(min = 8, message = "Password length must be at least 8 symbols")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "contact_name")
    private String contactName;

    @Email(message = "User email have to be valid!")
    @Column(name = "email")
    private String email;

    @Digits(integer = 10, fraction = 0, message = "Valid bulgarian number have to contain 10 digits!")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "user")
    private Set<Authority> authority;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Product> products;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password, String contactName, String email, String phoneNumber,
                String address, Set<Authority> authority, Set<Product> products) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.contactName = contactName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.authority = authority;
        this.products = products;
    }

    public User() {
    }

    public User(Long id, String username, String password, String contactName, String email, String phoneNumber, String address, Set<Authority> authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.contactName = contactName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(Set<Authority> authority) {
        this.authority = authority;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
