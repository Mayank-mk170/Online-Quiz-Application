package com.Online.Quiz.Application.Controller;

import com.Online.Quiz.Application.DTO.QuizDto;
import com.Online.Quiz.Application.Service.QuizService;
import com.Online.Quiz.Application.entity.Question;
import com.Online.Quiz.Application.entity.Quiz;
import com.Online.Quiz.Application.repository.QuizRepository;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private QuizService quizService;
    private QuizRepository quizRepository;

    public QuizController(QuizService quizService, QuizRepository quizRepository) {
        this.quizService = quizService;
        this.quizRepository = quizRepository;
    }
//
    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody Quiz quiz){
        if (quiz.getQuestions() != null) {
            for (Question question : quiz.getQuestions()) {
                question.setQuiz(quiz);
            }
        }
        Quiz savedQuiz = quizRepository.save(quiz);
        return ResponseEntity.ok(savedQuiz);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        return quizService.getAllQuiz();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable Long id){
        return quizService.getQuizById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz){
        return quizService.updateQuiz(id, quiz);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id){
        quizService.deleteQuiz(id);
        return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
    }

}
