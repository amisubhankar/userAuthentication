package com.eshop.userauth.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "user_auth")
@Getter
@Setter
public class User extends BaseModel {

    private String name;
    private String phoneNo;
    private String email;
    private String hashedPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

}
