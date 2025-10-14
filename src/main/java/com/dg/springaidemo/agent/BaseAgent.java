package com.dg.springaidemo.agent;

import cn.hutool.core.util.StrUtil;
import com.dg.springaidemo.agent.model.AgentState;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;


/**
 * 抽象基础代理类，用于管理代理状态和执行流程
 *
 * 提供状态转换、内存管理和基于步骤的执行循环的基础功能
 * 子类必须实现step方法
 */
@Data
@Slf4j
public abstract class BaseAgent {

    // 核心属性
    private String name;

    // 提示词
    private String systemPrompt;
    private String nextStepPrompt;

    // 状态
    private AgentState state = AgentState.IDLE;

    // 执行步骤控制
    private int currentStep = 0;
    private int maxSteps = 10;

    // LLM 大模型
    private ChatClient chatClient;

    // Memory 记忆（需要自主维护会话上下文）
    private List<Message> messageList = new ArrayList<>();

    /**
     * 运行代理
     *
     * @param userPrompt 用户提示词
     * @return 执行结果
     */
    public String run(String userPrompt){
        // 基础校验
        if(this.state != AgentState.IDLE){
            throw new RuntimeException("该状态不可运行：" + this.state);
        }
        if(StrUtil.isBlank(userPrompt)){
            throw new RuntimeException("用户提示词为空");
        }
        // 执行，更改状态
        this.state = AgentState.RUNNING;
        // 记录上下文信息
        messageList.add(new UserMessage(userPrompt));
        // 保存结果列表
        List<String> results = new ArrayList<>();
        try {
            // 执行循环
            for (int i = 0; i < maxSteps && state != AgentState.FINISHED; i++) {
                int stepNumber = i + 1;
                currentStep = stepNumber;
                log.info("Executing step {}/{}",stepNumber,maxSteps);
                // 单步执行
                String stepResult = step();
                String result = "Step " + stepNumber + ": " + stepResult;
                results.add(result);
            }
            // 检查是否超出步骤限制
            if(currentStep >= maxSteps){
                state = AgentState.FINISHED;
                results.add("结束： 最大为" + maxSteps + "步");
            }
            return String.join("\n",results);
        } catch (Exception e) {
            state = AgentState.ERROR;
            log.error("执行失败：" +  e);
            return "执行错误 " + e.getMessage();
        }finally {
            // 清理缓存
            cleanup();
        }
    }

    /**
     * 定义单个步骤
     *
     * @return
     */
    public abstract String step();

    /**
     * 清理资源
     */
    public void cleanup(){
        // 子类可以重写此方法来清理资源
    }
}
