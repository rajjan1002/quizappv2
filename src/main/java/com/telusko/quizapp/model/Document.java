package com.telusko.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String fileName;

    private Long fileSize;

    private Integer totalPages;

    private LocalDateTime uploadedAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    @OneToMany(mappedBy = "document",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<DocumentChunk> chunks = new ArrayList<>();
}