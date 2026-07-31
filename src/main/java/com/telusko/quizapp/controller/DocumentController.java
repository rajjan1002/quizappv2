package com.telusko.quizapp.controller;

import com.telusko.quizapp.model.Document;
import com.telusko.quizapp.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/document")
@CrossOrigin
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadPdf(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Document savedDocument = documentService.uploadPdf(file);

        return ResponseEntity.ok(savedDocument);
    }

}