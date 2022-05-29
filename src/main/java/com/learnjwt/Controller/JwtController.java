package com.learnjwt.Controller;

import com.learnjwt.Helper.javaUtil;
import com.learnjwt.model.JwtRequest;
import com.learnjwt.model.JwtResponse;
import com.learnjwt.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private javaUtil javaUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody JwtRequest jwtRequest){
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        }
        catch (UsernameNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Username not found");
        }
        catch (BadCredentialsException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unauthorized");
        }

        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtRequest.getUsername());

        String token = this.javaUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        jwtResponse.setExpires(10*60);

        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }
}
