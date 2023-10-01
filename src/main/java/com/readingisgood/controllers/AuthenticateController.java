package com.readingisgood.controllers;

import com.readingisgood.auth.Jwt;
import com.readingisgood.enities.AuthenticationRequest;
import com.readingisgood.enities.AuthenticationResponse;
import com.readingisgood.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {
    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final Jwt jwt;

    AuthenticateController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, Jwt jwt) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwt = jwt;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password.", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwt.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
