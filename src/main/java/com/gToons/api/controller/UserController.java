package com.gToons.api.controller;

import com.gToons.api.Jwt.JwtToken;
import com.gToons.api.domain.User;
import com.gToons.api.dto.UserRegisterLoginDto;
import com.gToons.api.exceptions.DuplicateUserException;
import com.gToons.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("/register")
    public User register(@RequestBody UserRegisterLoginDto userRegisterLoginDto){
        try {
            userService.verifyNewUser(userRegisterLoginDto.getUsername(), userRegisterLoginDto.getEmail());
        }catch (DuplicateUserException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
        catch (DataAccessResourceFailureException e){
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE, "Database not available"
            );
        }
        User u = userService.createUser(userRegisterLoginDto);
        return u;
        //return null;
    }
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRegisterLoginDto userRegisterLoginDto){//@RequestParam String username,@RequestParam String email,@RequestParam String password){
        String jwt = userService.login(userRegisterLoginDto);
        if(jwt == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password combo");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        return new ResponseEntity<>(headers,HttpStatus.OK);
    }
}