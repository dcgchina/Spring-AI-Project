package com.dg.springaidemo.tool;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceDownloadToolTest {

    @Test
    void downloadResource() {
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        String fileName = "logo.png";
        String url = "https://www.codefather.cn/logo.png";
        String result = resourceDownloadTool.downloadResource(url, fileName);
        assertNotNull(result);
    }
}
