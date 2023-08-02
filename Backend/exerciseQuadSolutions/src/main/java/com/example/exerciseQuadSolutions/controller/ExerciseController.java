package com.example.exerciseQuadSolutions.controller;

import com.example.exerciseQuadSolutions.domain.Question;
import com.example.exerciseQuadSolutions.domain.dto.QuestionResponse;
import com.example.exerciseQuadSolutions.services.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    private IQuestionService questionService;

    @GetMapping("/questions")
    public List<QuestionResponse> questions() {
        return questionService.getAllQuestions();
    }

    @PostMapping("/checkanswers")
    public List<String> checkAnswers(@RequestBody List<String> answers) {
        return questionService.checkAllAnswers(answers);
    }
}
