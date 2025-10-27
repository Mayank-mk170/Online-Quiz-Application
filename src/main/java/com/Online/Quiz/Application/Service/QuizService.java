package com.Online.Quiz.Application.Service;

import com.Online.Quiz.Application.entity.Quiz;
import com.Online.Quiz.Application.repository.QuestionRepository;
import com.Online.Quiz.Application.repository.QuizRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;

    // Create a new quiz
    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }
    public ResponseEntity<String> createQuize(Quiz quiz){
        if (quiz.getQuestions() != null) {
            quiz.getQuestions().forEach(q -> q.setQuiz(quiz));
        }
        quizRepository.save(quiz);
        return ResponseEntity.ok("✅ Quiz created and saved in database!");
            }

    // Get all quizzes
    public ResponseEntity<List<Quiz>> getAllQuiz(){
        return ResponseEntity.ok(quizRepository.findAll());
    }

    // Get quiz by id
    public ResponseEntity<?> getQuizById(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            return ResponseEntity.ok(quiz.get());
        }
        return ResponseEntity.badRequest().body("Quiz not found!");
    }
    // Update quiz
    public ResponseEntity<?> updateQuiz(Long id, Quiz updatedQuiz){
        Optional<Quiz> existingQuiz = quizRepository.findById(id);
        if(existingQuiz.isPresent()){
            Quiz quiz = existingQuiz.get();
            quiz.setTitle(updatedQuiz.getTitle());
            quizRepository.save(quiz);
            return ResponseEntity.ok("Quiz updated successfully!");
        }
        return ResponseEntity.badRequest().body("Quiz not found");
    }

    // Delete quiz
    public void deleteQuiz(Long id){
        quizRepository.deleteById(id);
    }

}
