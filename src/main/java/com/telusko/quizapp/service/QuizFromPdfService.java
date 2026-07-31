package com.telusko.quizapp.service;

import com.telusko.quizapp.dao.DocumentChunkDao;
import com.telusko.quizapp.model.DocumentChunk;
import com.telusko.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizFromPdfService {

    @Autowired
    private DocumentChunkDao chunkDao;

    @Autowired
    private AIQuestionGenerationService aiQuestionGenerationService;
    @Transactional
    public List<Question> generateQuizFromPdf(Integer documentId,
                                              int numberOfQuestions) {

        // Fetch all chunks of the uploaded PDF
        List<DocumentChunk> chunks = chunkDao.findByDocumentId(documentId);

        if (chunks == null || chunks.isEmpty()) {
            throw new RuntimeException("No content found for this document.");
        }

        // Merge all chunks into one context
        String context = chunks.stream()
                .map(DocumentChunk::getChunkText)
                .collect(Collectors.joining("\n\n"));

        // Generate questions from the complete PDF
        return aiQuestionGenerationService.generateQuestionsFromContext(
                context,
                numberOfQuestions,
                "PDF Quiz"
        );
    }
}