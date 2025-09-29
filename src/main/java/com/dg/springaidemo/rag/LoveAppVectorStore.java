package com.dg.springaidemo.rag;

import com.dg.springaidemo.app.LoveApp;
import jakarta.annotation.Resource;
import org.apache.ibatis.javassist.Loader;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Vector;

/**
 * 向量数据库配置（初始化基于内存的数据库 Bean）
 */
@Configuration
public class LoveAppVectorStore {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Bean
    VectorStore loveAppVector(EmbeddingModel dashscopeEmbeddingModel){
        SimpleVectorStore build = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
        build.add(documents);
        return build;
    }
}
