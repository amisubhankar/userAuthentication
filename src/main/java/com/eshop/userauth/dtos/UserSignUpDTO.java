package com.eshop.userauth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDTO {
    private String name;
    private String phoneNo;
    private String email;
    private String hashedPassword;
}
