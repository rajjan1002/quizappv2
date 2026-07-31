package com.telusko.quizapp.service;
import com.telusko.quizapp.model.ChatRequest;
import com.telusko.quizapp.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private OpenAiChatModel chatModel;

    public ChatResponse askQuestion(ChatRequest request) {

        // Retrieve only the chunks belonging to the selected document
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(request.getQuestion())
                        .topK(5)
                        .filterExpression("documentId == " + request.getDocumentId())
                        .build()
        );

        // No matching chunks found
        if (documents == null || documents.isEmpty()) {
            return new ChatResponse(
                    "I couldn't find any relevant information in the uploaded PDF."
            );
        }

        // Merge retrieved chunks into one context
        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        // Build prompt
        String prompt = """
                You are QuizForge AI, an intelligent PDF study assistant and programming tutor.
                
                Your primary responsibility is to help the user understand the uploaded PDF.
                
                Behavior Guidelines:
                
                1. If the user greets you (for example: "hi", "hello", "good morning"), respond naturally and politely like a friendly AI assistant.
                
                Example:
                User: "Hi"
                Assistant: "Hello! 👋 I'm your PDF assistant. How can I help you understand this document today?"
                
                2. If the user thanks you, respond politely.
                
                3. If the user makes casual conversation, respond naturally and briefly, then guide the conversation back to the uploaded PDF when appropriate.
                
                4. When answering questions about the uploaded PDF, always use the provided document context as the primary source of truth.
                
                5. If the PDF contains the relevant topic, explain it clearly using the document. You may simplify or elaborate on the explanation to improve understanding while staying consistent with the document.
                
                6. If the PDF only briefly mentions a concept (for example: "static", "JVM", "inheritance", "TCP", "REST API") without explaining it, you may provide a concise educational explanation from your general knowledge. Clearly mention that the concept is only referenced in the PDF and you are providing additional background for better understanding.
                
                Example:
                "The PDF mentions the 'static' keyword but doesn't explain it in detail. Here's a brief explanation..."
                
                7. If the user's question is closely related to a topic discussed in the PDF but requires additional background knowledge to understand it, provide that background before connecting it back to the document.
                
                8. If the answer is completely unrelated to the uploaded PDF and cannot reasonably help the user understand the document, politely explain that it isn't covered and guide the user back to questions about the PDF.
                
                9. Never fabricate information that claims to come from the PDF. Distinguish between:
                   - Information found in the PDF.
                   - Additional explanation provided from your own knowledge.
                
                10. Keep responses conversational, friendly, accurate, and concise. Use markdown formatting when it improves readability.
                
                Document Context:
                ----------------
                %s
                
                User Question:
                %s
                
             
             
             
                """.formatted(context, request.getQuestion());

        // Call GPT
        String answer = chatModel.call(new Prompt(prompt))
                .getResult()
                .getOutput()
                .getText();

        return new ChatResponse(answer);
    }
}