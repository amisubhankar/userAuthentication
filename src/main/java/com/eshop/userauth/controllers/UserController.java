package com.eshop.userauth.controllers;

import com.eshop.userauth.dtos.UserSignUpDTO;
import com.eshop.userauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/signup")
    public String signUp(@RequestBody UserSignUpDTO userSignUpDTO){
        userService.signUp(userSignUpDTO);
    }
}
