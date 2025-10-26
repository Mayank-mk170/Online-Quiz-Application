package com.Online.Quiz.Application.repository;

import com.Online.Quiz.Application.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUsername(String username);
}