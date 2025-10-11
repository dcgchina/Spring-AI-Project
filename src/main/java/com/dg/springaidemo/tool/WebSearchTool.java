package com.dg.springaidemo.tool;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 一个基于 SearchAPI (https://www.searchapi.io) 的网页搜索工具，
 * 用于 AI Agent 调用，返回结构化的搜索结果（如段落、代码、列表等）。
 *
 */
public class WebSearchTool {

    // 搜索 API 的固定地址
    private static final String SEARCH_API_URL = "https://www.searchapi.io/api/v1/search";

    // 用户传入的 API 密钥
    private final String apiKey;

    // 搜索引擎类型
    private static final String ENGINE = "baidu";

    /**
     * 构造函数
     *
     * @param apiKey
     */
    public WebSearchTool(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * AI Agent 可调用的搜索工具方法。
     * 使用 @Tool 注解标记，AI 可以理解这是一个“搜索网页”的工具。
     *
     * @param query 用户输入的搜索关键词，例如 "Fiber example in programming"
     * @return 返回结构化的搜索结果文本，主要提取自 text_blocks（段落、代码、列表等）
     */
    @Tool(description = "搜索互联网上的相关信息，返回结构化内容（如文本、代码、列表等），用于解答用户问题。")
    public String searchWeb(
            @ToolParam(description = "用户输入的搜索关键词") String query) {

        // 构造请求参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("q", query);
        paramMap.put("api_key", apiKey);
        paramMap.put("engine", ENGINE);

        try {
            // 使用 Hutool 发起 GET 请求（参数会自动拼接到 URL 上）
            String response = HttpUtil.get(SEARCH_API_URL, paramMap);

            // 解析返回的 JSON
            JSONObject jsonResponse = JSONUtil.parseObj(response);
            // 提取 organic_results 部分
            JSONArray organicResults = jsonResponse.getJSONArray("organic_results");
            List<Object> objects = organicResults.subList(0, 5);
            // 拼接搜索结果为字符串
            String result = objects.stream().map(obj -> {
                JSONObject tmpJsonObject = (JSONObject) obj;
                return tmpJsonObject.toString();
            }).collect(Collectors.joining(","));
            return result;
        } catch (Exception e) {
            return "Error searching Baidu: " + e.getMessage();
        }
    }

}