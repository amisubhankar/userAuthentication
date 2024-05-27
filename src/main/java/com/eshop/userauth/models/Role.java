package com.eshop.userauth.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user_roles")
@Getter
@Setter
public class Role extends BaseModel {
    private String name;
}
