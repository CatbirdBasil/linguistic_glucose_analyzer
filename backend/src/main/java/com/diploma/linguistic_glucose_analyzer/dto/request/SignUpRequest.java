package com.diploma.linguistic_glucose_analyzer.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.Instant;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 64)
    private String username;

    @Size(max = 32)
    private String firstName;

    @Size(max = 64)
    private String lastName;

    @NotBlank
    @Size(max = 128)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 128)
    private String password;

    @NotNull
    @Min(1)
    @Max(3)
    private Long diabetesTypeId;

    private Instant birthDate;

    private String role;
}
