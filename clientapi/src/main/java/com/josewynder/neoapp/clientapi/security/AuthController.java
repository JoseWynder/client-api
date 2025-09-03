package com.josewynder.neoapp.clientapi.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager,
                          JwtService jwtService,
                          UserService userService,
                          PasswordEncoder passwordEncoder){
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO register(@RequestBody AuthRequestDTO request){
        if(userService.findByUsername(request.username()).isPresent()){
            throw new RuntimeException("User already exists");
        }

        var user = new User(
                null,
                request.username(),
                request.password(),
                request.admin()
        );

        userService.save(user);

        String token = jwtService.generateToken(user.getUsername());
        return new TokenDTO(user.getUsername(), token);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody AuthRequestDTO request){
        try{
            var authToken = new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            );
            authManager.authenticate(authToken);

            String token = jwtService.generateToken(request.username());
            return new TokenDTO(request.username(), token);
        } catch (AuthenticationException e){
            throw new RuntimeException("Invalid username or password");
        }
    }
}
