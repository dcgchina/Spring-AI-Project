package com.dg.springaidemo.app;

import com.dg.springaidemo.advisor.CheckPromptAdvisor;
import com.dg.springaidemo.advisor.MyLoggerAdvisor;
import com.dg.springaidemo.chatmemory.DatabaseChatMemory;
import com.dg.springaidemo.chatmemory.FileBaseChatMemory;
import com.dg.springaidemo.rag.LoveAppRagCloudAdvisorConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class LoveApp {
    private final ChatClient chatClient;

    @Autowired
    private VectorStore loveAppVector;

    @Autowired
    private Advisor loveAppRagCloudAdvisor;

    private static final String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。" +
            "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
            "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
            "引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";

    /**
     * 初始化客户端
     * @param dashscopeChatModel
     */
    public LoveApp(ChatModel dashscopeChatModel){
        // 基于文件的对话记忆
        String fileDir = System.getProperty("user.dir") + "/tmp/chat-memory";
        ChatMemory chatMemory = new FileBaseChatMemory(fileDir);
//        ChatMemory chatMemory = new DatabaseChatMemory();
        // 基于内存的对话记忆
//        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        // 内存大小设置开启
                        new MessageChatMemoryAdvisor(chatMemory),
                        // 日志开启
                        new MyLoggerAdvisor()
                        // 开启违禁词拦截
//                        new CheckPromptAdvisor()
                        // 推理增强开启
//                        new ReReadingAdvisor()
                )
                .build();

    }

    /**
     * AI 对话（支持多轮对话记忆）
     * @param message
     * @param chatId
     * @return
     */
    public String doChat(String message,String chatId) {
        ChatResponse chatResponse = chatClient.prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);

        return content;
    }

    record LoveAppReport (String title, List<String> suggestions){}

    /**
     * AI 恋爱报告功能
     * @param message
     * @param chatId
     * @return
     */
    public LoveAppReport doChatWithReport(String message,String chatId) {
        LoveAppReport entity = chatClient.prompt()
                .user(message)
                .system(SYSTEM_PROMPT + "每次对话都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoveAppReport.class);
        log.info("LoveAppReport: {}", entity);

        return entity;
    }

    public String doChatWithRag(String message,String chatId){
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY,chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY,10))
                .advisors(
                        new MyLoggerAdvisor()
                )
                //基于本地RAG知识库的实现
                .advisors(new QuestionAnswerAdvisor(loveAppVector))
                // 基于阿里的远程RAG的实现
//                .advisors(loveAppRagCloudAdvisor)
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);

        return content;
    }

}
