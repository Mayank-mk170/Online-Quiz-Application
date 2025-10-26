package com.Online.Quiz.Application.Service;


import com.Online.Quiz.Application.DTO.LoginDto;
import com.Online.Quiz.Application.entity.Users;
import com.Online.Quiz.Application.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UsersRepository usersRepository;
    private JWTService jwtService;

    public UserService(UsersRepository usersRepository, JWTService jwtService) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
    }
    // SIGNUP AS USER
    public ResponseEntity<?> createUser(Users userDto){
        Optional<Users> userName = usersRepository.findByUsername(userDto.getUsername());
        if(userName.isPresent()){
            return new ResponseEntity<>("UserName already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Users> userEmail = usersRepository.findByEmail(userDto.getEmail());
            if(userEmail.isPresent()){
                return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String encryptedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
            userDto.setPassword(encryptedPassword);

            userDto.setRole("ROLE_USER");

            Users saveduser = usersRepository.save(userDto);
            return new ResponseEntity<>(saveduser,HttpStatus.CREATED);
        }

        // LOGIN VERIFY

    public String verifyLogin(LoginDto loginDto){
        Optional<Users> username = usersRepository.findByUsername(loginDto.getUsername());
        if (username.isPresent()){
            Users users = username.get();
            if(BCrypt.checkpw(loginDto.getPassword(), users.getPassword())){
                String token = jwtService.generateToken(users.getUsername());
                return token;
            }
        }else {
            return null;
        }
        return null;


    }

    // create admin user
    public ResponseEntity<?> createAdminUser(Users userDto, String requesterUsername){
        Optional<Users> adminUser = usersRepository.findByUsername((userDto.getUsername()));
        if(adminUser.isPresent()){
            return new ResponseEntity<>("Admin already enter", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Users> adminEmail = usersRepository.findByEmail(userDto.getEmail());
        if(adminEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String encryptedPassword = BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt(5));
        userDto.setPassword(encryptedPassword);

        userDto.setRole("ROLE_ADMIN");
        Users saveAdmin = usersRepository.save(userDto);
        return new ResponseEntity<>(saveAdmin, HttpStatus.CREATED);
    }

}

