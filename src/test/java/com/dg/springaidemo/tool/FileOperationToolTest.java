package com.dg.springaidemo.tool;

import com.dg.springaidemo.tool.FileOperationTool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileOperationToolTest {

    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String string = fileOperationTool.readFile("测试文档1号.txt");
        assertNotNull(string);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String writeFile = fileOperationTool.writeFile("测试文档1号.txt","你好世界！");
        assertNotNull(writeFile);
    }
}