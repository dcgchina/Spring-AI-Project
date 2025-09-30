package com.dg.springaidemo.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PgVectorStoreConfigTest {

    @Resource
    private VectorStore pgVectorVectorStore;

    @Test
    void pgVectorVectorStore() {
        List<Document> documents = List.of(
                new Document("在恋爱过程中怎样保持亲密度？拥抱啊、亲吻啊", Map.of("meta1", "meta1")),
                new Document("晨光的恋爱手册"),
                new Document("晨光这个小伙子比较帅气", Map.of("meta2", "meta2")));
        // 添加文档
        pgVectorVectorStore.add(documents);
        // 相似度查询
        List<Document> results = pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("怎么在恋爱过程中保持亲密度啊").topK(3).build());
        Assertions.assertNotNull(results);
    }
}