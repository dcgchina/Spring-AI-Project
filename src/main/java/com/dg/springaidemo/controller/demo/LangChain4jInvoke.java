package com.dg.springaidemo.controller.demo;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Value;

public class LangChain4jInvoke {

    @Value("${spring.ai.dashscope.api-key}")
    private static String API_KEY;

    public static void main(String[] args) {

        ChatLanguageModel qwenchatModel = QwenChatModel.builder()
                .apiKey(API_KEY)
                .modelName("qwen-max")
                .build();
        String chat = qwenchatModel.chat("你好我是程序员晨光，一名小白程序员！");
        System.out.println(chat);
    }
}
