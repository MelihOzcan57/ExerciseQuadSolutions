package com.example.exerciseQuadSolutions.services.impl;

import com.example.exerciseQuadSolutions.domain.Question;
import com.example.exerciseQuadSolutions.domain.dto.QuestionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.exerciseQuadSolutions.services.IQuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionService implements IQuestionService {
    private final String apiUrl = "https://opentdb.com/api.php?amount=5";
    private List<Question> fetchedQuestions = new ArrayList<>();
    @Override
    public List<QuestionResponse> getAllQuestions() {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode resultsNode = rootNode.get("results");

            List<Question> questions = objectMapper.readValue(
                    resultsNode.toString(),
                    new TypeReference<List<Question>>() {
                    }
            );

            return convertToQuestionResponses(questions);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle the exception appropriately
        }
    }

    @Override
    public List<String> checkAllAnswers(List<String> answers) {
        List<String> correctAnswers = new ArrayList<>();

        for (int i = 0; i < fetchedQuestions.size(); i++) {
            String correctAnswer = fetchedQuestions.get(i).getCorrect_answer();
            String selectedAnswer = answers.get(i);

            if (correctAnswer.equals(selectedAnswer)) {
                correctAnswers.add("Correct");
            } else {
                correctAnswers.add("Incorrect");
            }
        }
        return correctAnswers;
    }

    private List<String> preprocessQuestions(String correctAnswer, List<String> incorrectAnswers) {
        List<String> answers = incorrectAnswers;
        answers.add(correctAnswer); // Add the correct answer
        Collections.shuffle(answers); // Shuffle the answers
        return answers;
    }

    public List<QuestionResponse> convertToQuestionResponses(List<Question> questions) {
        fetchedQuestions = questions;
        List<QuestionResponse> questionResponses = new ArrayList<>();

        for (Question question : questions) {
            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setCategory(question.getCategory());
            questionResponse.setType(question.getType());
            questionResponse.setDifficulty(question.getDifficulty());
            questionResponse.setQuestion(question.getQuestion());
            questionResponse.setAnswers(preprocessQuestions(question.getCorrect_answer(), question.getIncorrect_answers()));

            questionResponses.add(questionResponse);
        }

        return questionResponses;
    }
}
