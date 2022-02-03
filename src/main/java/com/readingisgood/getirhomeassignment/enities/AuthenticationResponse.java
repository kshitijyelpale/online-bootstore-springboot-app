package com.readingisgood.getirhomeassignment.enities;

import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private final String jwtToken;

    public AuthenticationResponse(String jwt) {
        this.jwtToken = jwt;
    }
}
