package com.dg.springaidemo.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 向量数据库配置（初始化基于内存的数据库 Bean）
 */
@Configuration
public class LoveAppVectorStore {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    private MyKeyWordEnricher myKeyWordEnricher;

    @Bean
    VectorStore loveAppVector(EmbeddingModel dashscopeEmbeddingModel){
        SimpleVectorStore build = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        // 加载文档
        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
        // 自主切分文档
//        List<Document> splitCustomized = myTokenTextSplitter.splitCustomized(documents);
        //自动补充关键词元信息
        List<Document> enrichDocuments = myKeyWordEnricher.enrichDocuments(documents);
        build.add(enrichDocuments);
        return build;
    }
}
