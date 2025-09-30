package com.dg.springaidemo.rag;

import jakarta.annotation.Resource;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyKeyWordEnricher {

    @Resource
    private ChatModel dashscopeChatModel;

//    MyTokenTextSplitter(ChatModel chatModel){
//        this.chatModel = chatModel;
//    }

    public List<Document> enrichDocuments(List<Document> documents){
        KeywordMetadataEnricher metadataEnricher = new KeywordMetadataEnricher(dashscopeChatModel, 5);
        return metadataEnricher.apply(documents);
    }
}
