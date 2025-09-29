package com.dg.springaidemo.controller.demo;

import com.dg.springaidemo.controller.TestApiKey;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class LangChain4jInvoke {
    public static void main(String[] args) {
        ChatLanguageModel qwenchatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-max")
                .build();
        String chat = qwenchatModel.chat("你好我是程序员董晨光，一名小白程序员！");
        System.out.println(chat);
    }
}
