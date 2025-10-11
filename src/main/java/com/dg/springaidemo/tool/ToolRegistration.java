package com.dg.springaidemo.tool;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;

/**
 * 工具注册类
 */
public class ToolRegistration {

    @Value("${spring.search-api.api-key}")
    private String apiKey;

    public ToolCallback[] allTools(){
        FileOperationTool fileOperationTool = new FileOperationTool();
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        WebSearchTool webSearchTool = new WebSearchTool(apiKey);
        return ToolCallbacks.from(
                fileOperationTool,
                pdfGenerationTool,
                resourceDownloadTool,
                terminalOperationTool,webScrapingTool,
                webSearchTool
        );
    }
}
