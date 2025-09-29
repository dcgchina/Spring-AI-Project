package com.dg.springaidemo.chatmemory;

import com.dg.springaidemo.chatmemory.pojo.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper  // ⬅️ 必须加这个注解，让 MyBatis 扫描到这个接口
public interface ChatMessageMapper {

    // 插入一条消息
    @Insert("INSERT INTO chat_messages (conversation_id, role, content, timestamp) " +
            "VALUES (#{conversationId}, #{role}, #{content}, #{timestamp})")
    @Options(useGeneratedKeys = true, keyProperty = "id") // 返回自增主键到对象的 id 字段
    void insert(ChatMessage message);

    // 根据 conversationId 查询消息列表，按时间升序
    @Select("SELECT * FROM chat_messages WHERE conversation_id = #{conversationId} ORDER BY timestamp ASC")
    List<ChatMessage> findByConversationIdOrderByTimestampAsc(@Param("conversationId") String conversationId);

    // 根据 conversationId 删除所有消息
    @Delete("DELETE FROM chat_messages WHERE conversation_id = #{conversationId}")
    void deleteByConversationId(@Param("conversationId") String conversationId);
}