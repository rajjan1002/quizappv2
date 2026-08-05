package com.telusko.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuizappApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizappApplication.class, args);
	}

}
//[Frontend]
//		↓
//POST /quiz/create
//   ↓
//		   [Backend]
//		→ AI generates questions
//   → Save questions
//   → Save quiz
//   → Map quiz_questions
//   ↓
//Return quizId (30)
//   ↓
//		   [Frontend]
//		↓
//GET /quiz/get/30
//		↓
//		[Backend]
//		→ Fetch quiz
//   → Fetch questions (JOIN)
//   → Send safe data
//   ↓
//		   [Frontend shows quiz]