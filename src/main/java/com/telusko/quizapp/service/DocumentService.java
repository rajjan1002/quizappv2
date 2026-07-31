package com.telusko.quizapp.service;

import com.telusko.quizapp.dao.DocumentDao;
import com.telusko.quizapp.model.Document;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private ChunkService chunkService;

    @Autowired
    private EmbeddingService embeddingService;

    public Document uploadPdf(MultipartFile file) throws IOException {

        // Load PDF
        PDDocument pdf = Loader.loadPDF(file.getBytes());

        // Extract text
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(pdf);

        pdf.close();

        // Create document
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setContent(text);
        document.setUploadedAt(LocalDateTime.now());

        // Save document
        Document savedDocument = documentDao.save(document);

        // Create chunks
        chunkService.createChunks(savedDocument);

        // Generate embeddings
        embeddingService.indexDocument(savedDocument);

        return savedDocument;
    }


    /**
     * Fetch document by ID
     */
    public Document getDocumentById(Integer id) {

        return documentDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Document not found with id : " + id));
    }

}