package com.eshop.userauth.controllers;

import com.eshop.userauth.dtos.UserResponseDto;
import com.eshop.userauth.dtos.UserSignUpDTO;
import com.eshop.userauth.exceptions.DuplicateUserException;
import com.eshop.userauth.exceptions.UserNotFoundException;
import com.eshop.userauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignUpDTO){
        try {
            userService.signUp(userSignUpDTO);
        } catch (DuplicateUserException e) {
            return new ResponseEntity<>("User Already Exists !!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Successfully Added", HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<UserResponseDto> getUserDetails(@PathVariable("id") Long id){
        UserResponseDto userResponseDto = null;
        try {
            userResponseDto = userService.getUserDetailsById(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(userResponseDto);
        }

        return ResponseEntity.ok().body(userResponseDto);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Long> getUserIdByEmail(@PathVariable(name = "email") String email){
        return ResponseEntity.ok().body(userService.getUserIdByEmail(email));
    }
}
