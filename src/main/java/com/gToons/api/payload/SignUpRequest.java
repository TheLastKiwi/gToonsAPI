package com.gToons.api.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@Getter
@Setter
public class SignUpRequest {
    //TODO Maybe check this somewhere else or correct the error response sent when requirement not met
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
//    @Email pre validates an email but overrides error response
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

}
