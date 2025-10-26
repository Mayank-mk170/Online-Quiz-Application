package com.Online.Quiz.Application.repository;

import com.Online.Quiz.Application.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByUsername(String username);
  Optional<Users> findByEmail(String email);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  long countByRole(String role);

}