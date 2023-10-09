package com.geetodolist.todolistapps.controller;

import com.geetodolist.todolistapps.dto.jwt.JWTResponse;
import com.geetodolist.todolistapps.dto.users.RequestLogin;
import com.geetodolist.todolistapps.dto.users.RequestUser;
import com.geetodolist.todolistapps.entity.Users;
import com.geetodolist.todolistapps.security.CustomUserDetailsServices;
import com.geetodolist.todolistapps.service.UserService;
import com.geetodolist.todolistapps.util.JWTTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsServices customUserDetailsServices;
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, CustomUserDetailsServices customUserDetailsServices, JWTTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsServices = customUserDetailsServices;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Users> registerUser(@Valid @RequestBody RequestUser model){
        return new ResponseEntity<>(userService.createUser(model), HttpStatus.CREATED);
    }


    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<JWTResponse> loginUser(@Valid @RequestBody RequestLogin model)throws Exception{
        // validasi jika email dan password tidak terdaftar
        userService.validateEmailAndPassword(model);

        // authentikasi user
        authenticationUser(model.getEmail(), model.getPassword());

        final UserDetails details = customUserDetailsServices.loadUserByUsername(model.getEmail());
        final String token = jwtTokenUtil.generateToken(details);

        return new ResponseEntity<>(new JWTResponse(token), HttpStatus.OK);
    }

    // function authentication user
    private void authenticationUser(String email, String password)throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (DisabledException ex){
            throw new Exception("User Disable");
        }catch (BadCredentialsException ex){
            throw new Exception("Bad Credeantials");
        }
    }

}
