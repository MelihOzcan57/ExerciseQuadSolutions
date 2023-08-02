package com.example.exerciseQuadSolutions.services;

import com.example.exerciseQuadSolutions.domain.Question;
import com.example.exerciseQuadSolutions.domain.dto.QuestionResponse;

import java.util.List;

public interface IQuestionService {
    List<QuestionResponse> getAllQuestions();

    List<String> checkAllAnswers(List<String> answers);
}
