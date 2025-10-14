package com.dg.springaidemo.tool;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 工具注册类
 */
@Component
public class ToolRegistration {

    @Value("${spring.search-api.api-key}")
    private String apiKey;

    @Bean
    public ToolCallback[] allTools(){
        FileOperationTool fileOperationTool = new FileOperationTool();
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        WebSearchTool webSearchTool = new WebSearchTool(apiKey);
        TerminateTool terminateTool = new TerminateTool();
        return ToolCallbacks.from(
                fileOperationTool,
                terminateTool,
                pdfGenerationTool,
                resourceDownloadTool,
                terminalOperationTool,webScrapingTool,
                webSearchTool
        );
    }
}
