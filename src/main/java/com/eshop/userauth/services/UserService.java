package com.eshop.userauth.services;

import com.eshop.userauth.dtos.EmailFormatDto;
import com.eshop.userauth.dtos.UserResponseDto;
import com.eshop.userauth.dtos.UserSignUpDTO;
import com.eshop.userauth.exceptions.DuplicateUserException;
import com.eshop.userauth.exceptions.UserNotFoundException;
import com.eshop.userauth.models.User;
import com.eshop.userauth.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    //<Id type, message type>
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    //used for Serialize an object to string
    private ObjectMapper objectMapper;

    public void signUp(UserSignUpDTO userSignUpDTO) throws DuplicateUserException {
        Optional<User> user = userRepository.findByEmail(userSignUpDTO.getEmail());

        if(user.isPresent()){
            throw new DuplicateUserException();
        }
        else{
            User newUser = new User();
            newUser.setEmail(userSignUpDTO.getEmail());
            newUser.setHashedPassword(bCryptPasswordEncoder.encode(userSignUpDTO.getHashedPassword()));
            newUser.setName(userSignUpDTO.getName());
            newUser.setPhoneNo(userSignUpDTO.getPhoneNo());
            newUser.setHasEmailVerified(false);

            userRepository.save(newUser);

            try {
                kafkaTemplate.send("sendEshopEmail", objectMapper.writeValueAsString(getMessageForKafka(newUser)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private EmailFormatDto getMessageForKafka(User user) {
        EmailFormatDto message = new EmailFormatDto();
        message.setToEmail(user.getEmail());
        message.setSubject("Welcome onboard to Eshop !!");
        message.setContent("Hi " + user.getName() + ",\n\nYour profile got created at Eshop successfully\n\nHappy shopping !!" );

        return message;
    }

    public UserResponseDto getUserDetailsById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = optionalUser.get();;
        UserResponseDto userDetails = new UserResponseDto(user.getId(), user.getName(), user.getPhoneNo(), user.getEmail());
        return userDetails;
    }

    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email).get().getId();
    }
}
