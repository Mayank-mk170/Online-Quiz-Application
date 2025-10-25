package com.Online.Quiz.Application.Service;

import com.Online.Quiz.Application.entity.Question;
import com.Online.Quiz.Application.entity.Quiz;
import com.Online.Quiz.Application.repository.QuestionRepository;
import com.Online.Quiz.Application.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizAttemptService {
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;

    public QuizAttemptService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public Map<String, Object> takeQuiz(Long quizId, Map<Long, String> userAnswersMap){
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isEmpty()){
            throw new RuntimeException("Quiz not found with ID: " + quizId);
        }
        Quiz quiz = quizOptional.get();
        int score = 0;
        List<Map<String, Object>> feedbackList = new ArrayList<>();

        for (Question question : quiz.getQuestions()) {
            String correctAnswer = question.getCorrectAnswer();
            String submittedAnswer = userAnswersMap.get(question.getId());

            Map<String, Object> feedback = new HashMap<>();
            feedback.put("question", question.getTitle());
            feedback.put("yourAnswer", submittedAnswer);
            feedback.put("correctAnswer", correctAnswer);

            if (submittedAnswer != null && submittedAnswer.equalsIgnoreCase(correctAnswer)) {
                feedback.put("result", "Correct ");
                score++;
            } else {
                feedback.put("result", "Incorrect ");
            }
            feedbackList.add(feedback);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("quizTitle", quiz.getTitle());
        result.put("totalQuestions", quiz.getQuestions().size());
        result.put("score", score);
        result.put("feedback", feedbackList);

        return result;
    }
}
