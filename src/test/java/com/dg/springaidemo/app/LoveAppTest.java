package com.dg.springaidemo.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
class LoveAppTest {

    @Resource
    LoveApp loveApp;
    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        String message= "你好，我是程序员鱼皮";
        String answer = loveApp.doChat(message,chatId);

        message= "我最近遇到了情感上的问题，我想让我的另一半更爱我";
        answer = loveApp.doChat(message,chatId);
        Assertions.assertNotNull(answer);

        message= "你好，我是谁，帮我回忆一下";
        answer = loveApp.doChat(message,chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好我是程序员小白，我最近遇到了政治敏感的问题";
        LoveApp.LoveAppReport loveAppReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveAppReport);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer =  loveApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

}