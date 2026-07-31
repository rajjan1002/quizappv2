package com.telusko.quizapp.service;

import com.telusko.quizapp.dao.DocumentChunkDao;
import com.telusko.quizapp.model.Document;
import com.telusko.quizapp.model.DocumentChunk;
import org.springframework.ai.document.Document.Builder;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private DocumentChunkDao chunkDao;

    public void indexDocument(Document document) {

        List<DocumentChunk> chunks =
                chunkDao.findByDocumentId(document.getId());

        for (DocumentChunk chunk : chunks) {

            Map<String, Object> metadata = new HashMap<>();

            metadata.put("documentId", document.getId());
            metadata.put("chunkIndex", chunk.getChunkIndex());
            metadata.put("fileName", document.getFileName());

            org.springframework.ai.document.Document aiDocument =
                    new org.springframework.ai.document.Document(
                            chunk.getChunkText(),
                            metadata
                    );

            vectorStore.add(List.of(aiDocument));
        }

    }

}