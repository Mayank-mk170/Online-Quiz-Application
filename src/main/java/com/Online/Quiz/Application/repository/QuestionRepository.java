package com.Online.Quiz.Application.repository;

import com.Online.Quiz.Application.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}