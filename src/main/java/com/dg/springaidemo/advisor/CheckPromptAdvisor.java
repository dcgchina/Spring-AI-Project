package com.dg.springaidemo.advisor;


import com.dg.springaidemo.advisor.pojo.CheckPrompt;
import com.dg.springaidemo.advisor.service.CheckPromptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Lombok")
@Component
@Slf4j
public class CheckPromptAdvisor implements CallAroundAdvisor, StreamAroundAdvisor, Ordered {

    @Autowired
    CheckPromptService checkPromptService;



    private static final Logger log = LoggerFactory.getLogger(CheckPromptAdvisor.class);


//    private static final List<String> FORBIDDEN_WORDS = Arrays.asList(
//            "暴力", "毒品", "色情", "政治敏感", "反动", "诈骗", "违禁品", "攻击"
//    );

    /**
     * Advisor 执行顺序，数字越小优先级越高
     * 比如：设为 10，比日志优先级低（日志通常为 0），可让它先执行日志，再校验
     */
    @Override
    public int getOrder() {
        return 10; // 你可以调整，比如设为 5 或 20
    }

    /**
     * 拦截普通（单次）AI 调用
     */
    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        // 1. 前置：校验用户输入
        validatePrompt(advisedRequest.userText());

        // 2. 放行请求，继续调用 AI
        AdvisedResponse response = chain.nextAroundCall(advisedRequest);

        // 3. （可选）后置处理
        return response;
    }

    /**
     * 拦截流式（Streaming）AI 调用
     */
    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        // 1. 前置：校验用户输入
        validatePrompt(advisedRequest.userText());

        // 2. 放行请求，继续流式调用 AI
        Flux<AdvisedResponse> stream = chain.nextAroundStream(advisedRequest);

        // 3. （可选）你也可以在这里对每个流式响应做处理
        return stream;
    }

    /**
     * 校验用户 Prompt 是否包含违禁词
     */
    private void validatePrompt(String userPrompt) {
        if (userPrompt == null || userPrompt.trim().isEmpty()) {
            return;
        }

        String lowerPrompt = userPrompt.toLowerCase();
        List<CheckPrompt> selectAll = checkPromptService.findSelectAll();
        for (CheckPrompt word : selectAll) {
            if (lowerPrompt.contains(word.getCheckType())) {
                log.warn("检测到违禁词，阻止请求！违禁词: [{}], 输入内容: {}", word, userPrompt);
                throw new IllegalArgumentException("输入内容包含违禁词【" + word + "】，不允许提交！");
            }
        }
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
