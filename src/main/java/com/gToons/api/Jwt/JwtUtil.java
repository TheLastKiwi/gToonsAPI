package com.gToons.api.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gToons.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {



    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value(("${jwt.Issuer}"))
    private String issuer;
    private long expiration = 60 * 60 * 24 * 365 * 1000; //1 year
    public String generateToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return JWT.create()
                .withClaim("username",userPrincipal.getUsername())
                .withClaim("id", userPrincipal.getId())
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withSubject(Integer.toString(userPrincipal.getId()))
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public Integer getUserIdFromJWT(String token) {
        return Integer.parseInt(JWT.decode(token).getSubject());
    }

    public boolean validateToken(String authToken) {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(JWT_SECRET)).withIssuer(issuer).build();
        try {
            verifier.verify(authToken);
        }catch (JWTVerificationException e){
            return false;
        }

        return true;
    }


}
