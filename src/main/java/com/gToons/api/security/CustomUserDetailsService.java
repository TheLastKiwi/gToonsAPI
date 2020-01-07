package com.gToons.api.security;

import com.gToons.api.model.User;
import com.gToons.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username : ")
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Integer id)
        throws Exception{
        User user = userRepository.findById(id)
                .orElseThrow(
                        //TODO CHANGE EXCEPTION TYPE
            () -> new Exception("User ID:" + id)
        );

        return UserPrincipal.create(user);
        //return UserPrincipal.create(new User());
    }
}
