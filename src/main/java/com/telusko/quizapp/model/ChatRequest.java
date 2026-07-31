package com.telusko.quizapp.model;

import lombok.Data;

@Data
public class ChatRequest {

    private Integer documentId;

    private String question;

}