package com.Online.Quiz.Application.Config;


import com.Online.Quiz.Application.Service.JWTService;
import com.Online.Quiz.Application.entity.Users;
import com.Online.Quiz.Application.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtUtil extends OncePerRequestFilter {
    private JWTService jwtService;
    private UsersRepository usersRepository;

    public JwtUtil(JWTService jwtService, UsersRepository usersRepository) {
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
    }
    @Override
    protected  void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )throws ServletException, IOException{
        String token =  request.getHeader("Authorization");
        System.out.println(token);
        if(token!=null && token.startsWith("Bearer ")){
            String tokenVal = token.substring(7);
            System.out.println(tokenVal);
            String username = jwtService.getUserName(tokenVal);
            //System.out.println(username);
            Optional<Users> opUsername = usersRepository.findByUsername(username);
            if(opUsername.isPresent()){
                Users appUser = opUsername.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUser,null,
                        Collections.singleton(new SimpleGrantedAuthority(appUser.getRole() )));

                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request,response);
    }
}
