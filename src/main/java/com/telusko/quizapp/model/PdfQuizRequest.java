package com.telusko.quizapp.model;

import lombok.Data;

@Data
public class PdfQuizRequest {

    private Integer documentId;
    private Integer numberOfQuestions;

}