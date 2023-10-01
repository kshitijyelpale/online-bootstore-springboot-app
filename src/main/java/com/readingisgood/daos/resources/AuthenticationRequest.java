package com.readingisgood.daos.resources;

public record AuthenticationRequest(
        String username,
        String password
) {
}
