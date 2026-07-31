package com.telusko.quizapp.controller;

import com.telusko.quizapp.model.ChatRequest;
import com.telusko.quizapp.model.ChatResponse;
import com.telusko.quizapp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {

        ChatResponse response = chatService.askQuestion(request);

        return ResponseEntity.ok(response);
    }
}