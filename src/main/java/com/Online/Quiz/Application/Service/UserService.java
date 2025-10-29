package com.Online.Quiz.Application.Service;


import com.Online.Quiz.Application.DTO.LoginDto;
import com.Online.Quiz.Application.DTO.UserDto;
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
    public ResponseEntity<?> createUser(UserDto userDto){
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

        Users user = new Users();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(encryptedPassword);
        user.setRole("ROLE_USER");

        // Save user
        Users savedUser = usersRepository.save(user);
            return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
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
    public ResponseEntity<?> createAdminUser(UserDto userDto, String requesterUsername){
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

        Users admin = new Users();
        admin.setName(userDto.getName());
        admin.setUsername(userDto.getUsername());
        admin.setEmail(userDto.getEmail());
        admin.setPassword(encryptedPassword);
        admin.setRole("ROLE_ADMIN");

        Users savedAdmin = usersRepository.save(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

}

