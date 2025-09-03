package com.josewynder.neoapp.clientapi.security;

public record AuthRequestDTO(String login, String password, boolean admin) {
}
