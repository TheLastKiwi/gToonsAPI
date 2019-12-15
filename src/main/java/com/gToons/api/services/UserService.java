package com.gToons.api.services;

import com.gToons.api.Jwt.JwtToken;
import com.gToons.api.Jwt.JwtUtil;
import com.gToons.api.domain.User;
import com.gToons.api.dto.UserRegisterLoginDto;
import com.gToons.api.exceptions.DuplicateUserException;
import com.gToons.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = passwordEncoder;
        this.jwtUtil= jwtUtil;
    }

    /**
     * Creates the user and inserts it into the database
     *
     * @param email
     * @param username
     * @param password
     * @return created user if save is successful
     * @throws org.springframework.dao.DataAccessResourceFailureException if database not available
     */
    public User createUser(UserRegisterLoginDto userRegisterLoginDto) {
        User user = User.builder()
                .email(userRegisterLoginDto.getEmail())
                .username(userRegisterLoginDto.getUsername())
                .passwordHash(encoder.encode(userRegisterLoginDto.getPassword()))
                .build();
        userRepository.save(user);
        System.out.println("Password: " + userRegisterLoginDto.getPassword() + " || Hash sent to DB: " + user.getPasswordHash());
        System.out.println("Match? " + encoder.matches(userRegisterLoginDto.getPassword(),user.getPasswordHash()));
        return user;
    }

    /**
     * Verifies that there is no user in the database before with the identical username or email before creating the user
     *
     * @param username
     * @param email
     * @throws DuplicateUserException when the given username or email already exist in the database
     */
    public void verifyNewUser(String username, String email) throws DuplicateUserException {
        DuplicateUserException ex = null;

        if (userRepository.findTopByEmail(email) != null) {
            //Email already exists
            ex = new DuplicateUserException("Email already exists");
        }

        if (userRepository.findTopByUsername(username) != null) {
            //Username already exists
            if (ex == null) {
                ex = new DuplicateUserException("User already exists");
            }
        }
        if (ex != null) {
            throw ex;
        }
    }

    public String login(UserRegisterLoginDto urlDto){

        User user = userRepository.findTopByUsername(urlDto.getUsername());

        if(encoder.matches(urlDto.getPassword(), user.getPasswordHash())){
            return jwtUtil.create(user);
        }
        return null;
    }
}