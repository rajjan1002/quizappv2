package com.telusko.quizapp.controller;

import com.telusko.quizapp.model.PdfQuizRequest;
import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.service.QuizFromPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pdf-quiz")
@CrossOrigin
public class PdfQuizController {

    @Autowired
    private QuizFromPdfService quizFromPdfService;

    @PostMapping
    public List<Question> generateQuiz(@RequestBody PdfQuizRequest request) {

        return quizFromPdfService.generateQuizFromPdf(
                request.getDocumentId(),
                request.getNumberOfQuestions()
        );
    }
}