package com.dg.springaidemo;

import com.dg.springaidemo.chatmemory.pojo.ChatMessage;
import com.dg.springaidemo.chatmemory.service.ChatMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class testOne {

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Test
    void ddd(){
        try{
        // 执行命令
            Process process = Runtime.getRuntime().exec( "D:\\dongc\\Note\\Typora\\Typora.exe");
        // 等待外部程序结束
            int exitCode = process.waitFor();
        // 检查执行结果
            if (exitCode == 0) {
                System.out.println("命令成功执行");
            }else {
                System.out.println("命令执行失败");
            }
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() {
        List<ChatMessage> all = chatMessageRepository.findAll();

        all.stream()
                .forEach(msg ->
                        System.out.println("ID: " + msg.getId() +
                                ", ConversationId: " + msg.getConversationId() +
                                ", Role: " + msg.getRole() +
                                ", Content: " + msg.getContent() +
                                ", Timestamp: " + msg.getTimestamp())
                );
    }

}
