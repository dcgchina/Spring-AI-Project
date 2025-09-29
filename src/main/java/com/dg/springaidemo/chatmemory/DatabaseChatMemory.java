package com.dg.springaidemo.chatmemory;


import com.dg.springaidemo.chatmemory.pojo.ChatMessage;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseChatMemory implements ChatMemory {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<ChatMessage> dbMessages = messages.stream()
                .map(msg -> new ChatMessage(
                        conversationId,
                        msg.getMessageType().name(),         // "user" 或 "assistant"
                        msg.getText(),
                        LocalDateTime.now()
                ))
                .collect(Collectors.toList());

        dbMessages.forEach(chatMessageMapper::insert);  // 插入每一条消息
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<ChatMessage> dbMessages = chatMessageMapper.findByConversationIdOrderByTimestampAsc(conversationId);

        int fromIndex = Math.max(0, dbMessages.size() - lastN);
        List<ChatMessage> subList = dbMessages.subList(fromIndex, dbMessages.size());

        return subList.stream().map(dbMsg -> {
            switch (dbMsg.getRole()) {
                case "user":
                    return new UserMessage(dbMsg.getContent());
                case "assistant":
                    return new AssistantMessage(dbMsg.getContent());
                default:
                    return new UserMessage(dbMsg.getContent());
            }
        }).collect(Collectors.toList());
    }

    @Override
    public void clear(String conversationId) {
        chatMessageMapper.deleteByConversationId(conversationId);
    }
}