package com.Online.Quiz.Application.Service;

import com.Online.Quiz.Application.entity.Question;
import com.Online.Quiz.Application.entity.Quiz;
import com.Online.Quiz.Application.entity.QuizAttempt;
import com.Online.Quiz.Application.repository.QuestionRepository;
import com.Online.Quiz.Application.repository.QuizAttemptRepository;
import com.Online.Quiz.Application.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Map;
import java.util.Optional;

@Service
public class QuizAttemptService {
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;
    private QuizAttemptRepository quizAttemptRepository;

    public QuizAttemptService(QuizRepository quizRepository, QuestionRepository questionRepository, QuizAttemptRepository quizAttemptRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    public Map<String, Object> takeQuiz(String username,Long quizId, Map<Long, String> userAnswersMap){
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isEmpty()){
            throw new RuntimeException("Quiz not found with ID: " + quizId);
        }
        Quiz quiz = quizOptional.get();
        int score = 0;
        int correctCount = 0;
        int incorrectCount = 0;
        int notAttemptedCount = 0;
        List<Map<String, Object>> feedbackList = new ArrayList<>();

        for (Question question : quiz.getQuestions()) {
            String correctAnswer = question.getCorrectAnswer();
            String submittedAnswer = userAnswersMap.get(question.getId());

            String resultTest;
            if(submittedAnswer == null){
                resultTest = "Not attempted";
                notAttemptedCount++;
            }else if(submittedAnswer.equalsIgnoreCase(correctAnswer)){
                resultTest = "correct";
                score++;
                correctCount++;
            }else {
                resultTest = "Incorrect";
                incorrectCount++;
            }

            LinkedHashMap<String, Object> feedback = new LinkedHashMap<>();

            feedback.put("question", question.getTitle());

            LinkedHashMap<String, String> options = new LinkedHashMap<>();
            options.put("A", question.getOptionA());
            options.put("B", question.getOptionB());
            options.put("C", question.getOptionC());
            options.put("D", question.getOptionD());
            feedback.put("option", options);

            feedback.put("yourAnswer", submittedAnswer != null ? submittedAnswer : "Not attempted");
            feedback.put("result", resultTest);
            feedback.put("correctAnswer", correctAnswer);



            feedbackList.add(feedback);
        }
        QuizAttempt attempt = new QuizAttempt();
        attempt.setUsername(username);
        attempt.setQuiz(quiz);
        attempt.setScore(score);
        attempt.setTotalQuestion(quiz.getQuestions().size());
        attempt.setAttemptedAt(LocalDateTime.now());
        quizAttemptRepository.save(attempt);

        //Result
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("quizTitle",quiz.getTitle());
        result.put("username", username);
        result.put("totalQuestions", quiz.getQuestions().size());
        result.put("score",score);
        result.put("correctCount", correctCount);
        result.put("incorrectCount", incorrectCount);
        result.put("notAttemptedCount", notAttemptedCount);
        result.put("attemptedAt", attempt.getAttemptedAt());
        result.put("feedback", feedbackList);

        return result;

    }
    public List<QuizAttempt> getUserAttempts(String username) {
        return quizAttemptRepository.findByUsername(username);
    }
    public QuizAttempt saveAttempt(QuizAttempt attempt) {
        return quizAttemptRepository.save(attempt);
    }

    public List<QuizAttempt> getAllAttempts() {
        return quizAttemptRepository.findAll();
    }

}
