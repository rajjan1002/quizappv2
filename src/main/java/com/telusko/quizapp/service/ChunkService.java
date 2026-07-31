package com.telusko.quizapp.service;

import com.telusko.quizapp.dao.DocumentChunkDao;
import com.telusko.quizapp.model.Document;
import com.telusko.quizapp.model.DocumentChunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChunkService {

    @Autowired
    private DocumentChunkDao chunkDao;

    private static final int CHUNK_SIZE = 1000;

    public void createChunks(Document document) {

        String text = document.getContent();

        int chunkIndex = 1;

        for(int start=0;
            start<text.length();
            start+=CHUNK_SIZE){

            int end=Math.min(start+CHUNK_SIZE,text.length());

            String chunk=text.substring(start,end);

            DocumentChunk documentChunk=new DocumentChunk();

            documentChunk.setChunkIndex(chunkIndex++);

            documentChunk.setChunkText(chunk);

            documentChunk.setDocument(document);

            chunkDao.save(documentChunk);

        }

    }

}