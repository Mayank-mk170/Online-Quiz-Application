package com.Online.Quiz.Application.Controller;


import com.Online.Quiz.Application.DTO.LoginDto;
import com.Online.Quiz.Application.DTO.TokenDto;
import com.Online.Quiz.Application.DTO.UserDto;
import com.Online.Quiz.Application.Service.JWTService;
import com.Online.Quiz.Application.Service.UserService;
import com.Online.Quiz.Application.entity.Users;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;
@Validated
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private UserService userService;
    private JWTService jwtService;

    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // Signup As User
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }


    // Signup as Admin
    @PostMapping("/signup-admin")
    public ResponseEntity<?> createAdmin(
          @Valid  @RequestBody UserDto userDto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String requesterUsername = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // remove 'Bearer '
            requesterUsername = jwtService.getUserName(token);
        }

        return userService.createAdminUser(userDto, requesterUsername);
    }



    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String token = userService.verifyLogin(loginDto);
        if(token!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
        }
    }
}
