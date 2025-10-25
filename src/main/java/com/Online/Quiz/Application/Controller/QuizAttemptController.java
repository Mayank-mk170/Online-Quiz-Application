package com.Online.Quiz.Application.Controller;

import com.Online.Quiz.Application.Service.QuizAttemptService;
import com.Online.Quiz.Application.Service.QuizService;
import com.Online.Quiz.Application.entity.Quiz;
import com.Online.Quiz.Application.repository.QuizRepository;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/quiz-attempt")
public class QuizAttemptController {

    private QuizRepository quizRepository;
    private QuizAttemptService quizAttemptService;
    private QuizService quizService;

    public QuizAttemptController(QuizRepository quizRepository, QuizAttemptService quizAttemptService, QuizService quizService) {
        this.quizRepository = quizRepository;
        this.quizAttemptService = quizAttemptService;
        this.quizService = quizService;
    }
    // List all Quiz
    @GetMapping("/list")
    public ResponseEntity<?> listQuizzes() {
        return ResponseEntity.ok(quizRepository.findAll());
    }

    // Get question for quiz
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<?> getQuizQuestion(@PathVariable Long quizId){
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isEmpty()) {
            return ResponseEntity.badRequest().body("Quiz not found");
        }
        return ResponseEntity.ok(quiz.get().getQuestions());
    }


    // Submit answers
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<?> submitQuiz(
            @PathVariable Long quizId,
            @RequestBody Map<String, String> userAnswers) {

        Map<Long, String> answersMap = new HashMap<>();
        for(Map.Entry<String, String> entry : userAnswers.entrySet()){
            answersMap.put(Long.parseLong(entry.getKey()), entry.getValue());
        }
        Map<String, Object> result = quizAttemptService.takeQuiz(quizId, answersMap);
        return ResponseEntity.ok(result);
    }


}

