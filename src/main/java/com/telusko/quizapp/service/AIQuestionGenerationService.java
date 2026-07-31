package com.telusko.quizapp.service;

import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.model.QuestionDTO;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIQuestionGenerationService {

    @Autowired
    private OpenAiChatModel chatModel;

    // ===========================
    // Existing Feature
    // Generate questions by category
    // ===========================
    public List<Question> generateQuestions(String category, int numQ) {

        BeanOutputConverter<QuestionDTO[]> converter =
                new BeanOutputConverter<>(QuestionDTO[].class);

        String prompt = """
Generate %d multiple choice questions on topic %s.

Each question MUST include:
- questionTitle
- option1
- option2
- option3
- option4
- rightAnswer
- category (must be "%s")
- difficultyLevel (easy, medium, or hard)

%s
""".formatted(
                numQ,
                category,
                category,
                converter.getFormat()
        );

        return generateFromPrompt(prompt, converter);
    }


    // ===========================
    // NEW FEATURE
    // Generate questions from PDF Context
    // ===========================
    public List<Question> generateQuestionsFromContext(String context,
                                                       int numQ,
                                                       String category) {

        BeanOutputConverter<QuestionDTO[]> converter =
                new BeanOutputConverter<>(QuestionDTO[].class);

        String prompt = """
You are an AI Quiz Generator.

Generate %d multiple choice questions ONLY from the study material below.

IMPORTANT RULES:

1. Do NOT use outside knowledge.
2. Questions must come ONLY from the provided context.
3. Every question should have:
   - questionTitle
   - option1
   - option2
   - option3
   - option4
   - rightAnswer
   - category ("%s")
   - difficultyLevel (easy, medium, or hard)

Study Material:

%s

%s
""".formatted(
                numQ,
                category,
                context,
                converter.getFormat()
        );

        return generateFromPrompt(prompt, converter);
    }


    // ===========================
    // Common AI Logic
    // ===========================
    private List<Question> generateFromPrompt(String prompt,
                                              BeanOutputConverter<QuestionDTO[]> converter) {

        Prompt promptObj = new Prompt(prompt);

        String response = chatModel.call(promptObj)
                .getResult()
                .getOutput()
                .getText();

        QuestionDTO[] dtoArray = converter.convert(response);

        List<Question> questions = new ArrayList<>();

        for (QuestionDTO dto : dtoArray) {

            Question q = new Question();

            q.setQuestionTitle(dto.getQuestionTitle());
            q.setOption1(dto.getOption1());
            q.setOption2(dto.getOption2());
            q.setOption3(dto.getOption3());
            q.setOption4(dto.getOption4());
            q.setRightAnswer(dto.getRightAnswer());
            q.setCategory(dto.getCategory());
            q.setDifficultyLevel(dto.getDifficultyLevel());

            questions.add(q);
        }

        return questions;
    }
}