package com.eshop.userauth.services;

import com.eshop.userauth.dtos.UserSignUpDTO;
import com.eshop.userauth.models.User;
import com.eshop.userauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void signUp(UserSignUpDTO userSignUpDTO){
        Optional<User> user = userRepository.findByEmail(userSignUpDTO.getEmail());

        if(user.isPresent()){
            throw new DuplicateUserException("User already exists");
        }
        else{
            User newUser = new User();
            newUser.setEmail(userSignUpDTO.getEmail());
            newUser.setHashedPassword(userSignUpDTO.getHashedPassword());
            newUser.setName(userSignUpDTO.getName());
            newUser.setPhoneNo(userSignUpDTO.getPhoneNo());

            userRepository.save(newUser);
        }

    }
}
