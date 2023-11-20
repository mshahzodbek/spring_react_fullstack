package com.shahzoddev.AssignmentSubmissionApp.controller;

import com.shahzoddev.AssignmentSubmissionApp.domain.User;
import com.shahzoddev.AssignmentSubmissionApp.dto.AuthCredentialsRequest;
import com.shahzoddev.AssignmentSubmissionApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest req){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

//        if (authentication.isAuthenticated()){
//            return jwtUtil.generatedToken(req.getUsername());
//        }else{
//            throw new UsernameNotFoundException("Invalid user credentials");
//        }

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,
                jwtUtil.generatedToken(user)
                ).body(user);

    }
}
