package com.telusko.quizapp.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vector")
public class TestController {

    @Autowired
    private VectorStore vectorStore;

    @GetMapping
    public String test(){

        List<Document> docs =
                vectorStore.similaritySearch("What is JVM?");

        return docs.toString();

    }

}