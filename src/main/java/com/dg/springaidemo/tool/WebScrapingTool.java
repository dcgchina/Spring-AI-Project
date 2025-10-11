package com.dg.springaidemo.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.IOException;

public class WebScrapingTool {

    /**
     * AI 可调用的工具方法：抓取指定 URL 的网页内容，返回标题和正文
     *
     * @param url 目标网页地址，例如 "https://example.com/news/123"
     * @return 结构化文本，包含标题和正文内容，适合 AI 阅读
     */
    @Tool(description = "抓取指定网页的标题和主要内容，返回结构化文本，供 AI 阅读和总结。请传入有效的网页 URL。")
    public String scrapeWebPage(
            @ToolParam(description = "要抓取的目标网页的完整 URL，例如：https://www.bbc.com/news/world-12345678") String url) {

        try {
            // 使用 Jsoup 抓取网页
            Document doc = Jsoup.connect(url).get();

            // 提取标题
            String title = doc.title();

            // 提取正文：一般选取 <body> 中的文本内容，或者更精确的选择器如 article、main、.content 等
            // 这里简单提取 body 的文本，你可以根据目标网站结构调整选择器
            Element body = doc.body();
            String bodyText = body.text();

            // 可选：更精确的正文提取（比如 <article> 标签，或者 .post-content 等）
            // Element article = doc.selectFirst("article");
            // String bodyText = article != null ? article.text() : body.text();

            // 拼接返回给 AI 的结构化内容
            StringBuilder result = new StringBuilder();
            result.append("🔗 网页标题：").append(title).append("\n\n");
            result.append("📄 网页正文（节选/摘要）：\n");
            result.append(bodyText.length() > 1000 ? bodyText.substring(0, 1000) + "..." : bodyText);

            return result.toString();

        } catch (IOException e) {
            return "❌ 抓取网页失败，URL 可能无效或网络错误: " + e.getMessage();
        } catch (Exception e) {
            return "❌ 抓取网页时发生异常: " + e.getMessage();
        }
    }
}
