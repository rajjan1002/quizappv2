package com.telusko.quizapp.dao;

import com.telusko.quizapp.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDao extends JpaRepository<Document, Integer> {

}