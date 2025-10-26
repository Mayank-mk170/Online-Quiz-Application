package com.Online.Quiz.Application.DTO;

import com.Online.Quiz.Application.entity.Question;

import java.util.List;

public class QuizDto {
    private Long id;
    private String title;
    private List<QuestionDto> question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionDto> getQuestion() {
        return question;
    }

    public void setQuestion(List<QuestionDto> question) {
        this.question = question;
    }


}
