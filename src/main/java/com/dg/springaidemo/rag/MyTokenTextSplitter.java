package com.dg.springaidemo.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

import java.util.List;

public class MyTokenTextSplitter {

    // 使用默认值 切分器
    public List<Document> splitDocuments(List<Document> documents){
        TokenTextSplitter splitter = new TokenTextSplitter();
        return splitter.apply(documents);
    }

    // 加入限制
    public List<Document> splitCustomized(List<Document> documentList){
        TokenTextSplitter textSplitter = new TokenTextSplitter(200,100,10,5000,true);
        return textSplitter.apply(documentList);
    }
}
