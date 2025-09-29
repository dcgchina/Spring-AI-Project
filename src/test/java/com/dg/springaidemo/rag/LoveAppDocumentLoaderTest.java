package com.dg.springaidemo.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.ReadOnlyProperty;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoveAppDocumentLoaderTest {

    @Resource
    LoveAppDocumentLoader loveAppDocumentLoader;

    @Test
    void loadMarkdowns() {
        loveAppDocumentLoader.loadMarkdowns();
    }
}