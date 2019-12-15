package com.gToons.api.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gToons.api.domain.User;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    //@Autowired
    //Environment environment;

    @Value("${jwt.secret}")
    private String JWT_SECRET;// = environment.;
    private long expiration = 60 * 60 * 24 * 365 * 1000; //1 year
    public String create(User user){
        return JWT.create()
                .withClaim("username",user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

}
