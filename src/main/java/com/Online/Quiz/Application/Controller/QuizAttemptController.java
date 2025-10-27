package com.Online.Quiz.Application.Controller;

import com.Online.Quiz.Application.Service.QuizAttemptService;
import com.Online.Quiz.Application.Service.QuizService;
import com.Online.Quiz.Application.entity.Quiz;
import com.Online.Quiz.Application.entity.QuizAttempt;
import com.Online.Quiz.Application.repository.QuizAttemptRepository;
import com.Online.Quiz.Application.repository.QuizRepository;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/quiz-attempt")
public class  QuizAttemptController {

    private QuizRepository quizRepository;
    private QuizAttemptService quizAttemptService;
    private QuizService quizService;
    private final QuizAttemptRepository quizAttemptRepository;

    public QuizAttemptController(QuizRepository quizRepository, QuizAttemptService quizAttemptService, QuizService quizService,
                                 QuizAttemptRepository quizAttemptRepository) {
        this.quizRepository = quizRepository;
        this.quizAttemptService = quizAttemptService;
        this.quizService = quizService;
        this.quizAttemptRepository = quizAttemptRepository;
    }
    // List all Quiz
    @GetMapping("/list")
    public ResponseEntity<?> listQuizzes() {
        return ResponseEntity.ok(quizRepository.findAll());
    }


    // Submit answers
    @PostMapping("/submit/{quizId}/{username}")
    public Map<String, Object> submitQuiz(
            @PathVariable Long quizId,
            @PathVariable String username,
            @RequestBody Map<Long, String> userAnswersMap) {
        return quizAttemptService.takeQuiz(username, quizId, userAnswersMap);
    }


   // Get attempts quiz by user
   @GetMapping("/user/{username}")
   public List<Map<String, Object>> getUserAttempts(@PathVariable String username) {
       return quizAttemptService.getUserAttempts(username);
   }

    // Admin can view all quiz attempts
    @GetMapping("/all")
    public ResponseEntity<?> getAllAttempts() {
        List<Map<String, Object>> allAttempts = new ArrayList<>();

        quizAttemptRepository.findAll().forEach(attempt -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", attempt.getId());
            map.put("username", attempt.getUsername());
            map.put("quizTitle", attempt.getQuiz() != null ? attempt.getQuiz().getTitle() : null);
            map.put("score", attempt.getScore());
            map.put("totalQuestion", attempt.getTotalQuestion());
            map.put("attemptedAt", attempt.getAttemptedAt());
            allAttempts.add(map);
        });

        return ResponseEntity.ok(allAttempts);
    }
}

