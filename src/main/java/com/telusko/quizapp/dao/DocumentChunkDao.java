package com.telusko.quizapp.dao;

import com.telusko.quizapp.model.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentChunkDao
        extends JpaRepository<DocumentChunk,Integer> {

    List<DocumentChunk> findByDocumentId(Integer id);

}