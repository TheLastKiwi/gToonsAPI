package com.gToons.api.payload;

import lombok.Getter;

/**
 * Created by rajeevkumarsingh on 19/08/17.
 */
@Getter
public class JwtAuthenticationResponse {
    private String token = null;
    private  String tokenType = "jwt";

    public JwtAuthenticationResponse(String accessToken) {
        this.token = accessToken;
    }
}
