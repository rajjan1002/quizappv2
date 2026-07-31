package com.telusko.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer chunkIndex;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String chunkText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;
}