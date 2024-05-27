package com.eshop.userauth.controllers;

import com.eshop.userauth.dtos.UserSignUpDTO;
import com.eshop.userauth.exceptions.DuplicateUserException;
import com.eshop.userauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignUpDTO){
        try {
            userService.signUp(userSignUpDTO);
        } catch (DuplicateUserException e) {
            return new ResponseEntity<>("User Already Exists !!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Successfully Added", HttpStatus.OK);
    }

}
