package com.dg.springaidemo.rag;

import ch.qos.logback.classic.boolex.MarkerList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
public class LoveAppDocumentLoader {

    private ResourcePatternResolver resourcePatternResolver;
    LoveAppDocumentLoader(ResourcePatternResolver resourcePatternResolver){
        this.resourcePatternResolver = resourcePatternResolver;
    }

    /**
     * 本地Markdown文档RAG知识库实现
     * @return
     */
    public List<Document> loadMarkdowns(){
        List<Document> allDocuments = new ArrayList<>();
        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath:document/*.md");
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)
                        .withIncludeCodeBlock(false)
                        .withIncludeBlockquote(false)
                        .withAdditionalMetadata("filename", filename)
                        .build();
                MarkdownDocumentReader documentReader = new MarkdownDocumentReader(resource, config);
                allDocuments.addAll(documentReader.get());
            }
        } catch (IOException e) {
            log.error("Markdown文档 加载失败！" + e);
        }
        return allDocuments;
    }
}
