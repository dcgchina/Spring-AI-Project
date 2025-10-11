package com.dg.springaidemo.tool;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        String pdfName = "测试PDF文件.pdf";
        String contant = "测试内容pdf文件……";
        String result = pdfGenerationTool.generatePDF(pdfName, contant);
        assertNotNull(result);
    }
}