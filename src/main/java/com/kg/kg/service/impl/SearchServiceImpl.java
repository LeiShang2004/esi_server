package com.kg.kg.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.kg.common.CommonResponse;
import com.kg.kg.entities.Paper;
import com.kg.kg.repository.SearchMapper;
import com.kg.kg.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


@Service("SearchService")
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    //按照文章标题搜索
    public CommonResponse<List<Paper>> getPaperByTitle(String title) {
        String cacheKey = "paperSearch:" + title;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                List<Paper> cachedData = objectMapper.readValue(cacheValue, new TypeReference<List<Paper>>() {
                });
                return CommonResponse.createForSuccess(cachedData);
            } catch (IOException e) {
                e.printStackTrace(); // JSON 解析错误处理
            }
        }

        List<Paper> papers = searchMapper.getPaperByTitle(title);
        if (papers.isEmpty()) {
            return CommonResponse.createForError("没有找到匹配的论文，请尝试其他标题字段");
        }

        try {
            String jsonData = objectMapper.writeValueAsString(papers);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return CommonResponse.createForSuccess(papers);
    }


    //按照文章作者搜索
    public CommonResponse<List<Paper>> getPaperByAuthor(String author) {
        String cacheKey = "paperSearch:" + author;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                List<Paper> cachedData = objectMapper.readValue(cacheValue, new TypeReference<List<Paper>>() {
                });
                return CommonResponse.createForSuccess(cachedData);
            } catch (IOException e) {
                e.printStackTrace(); // JSON 解析错误处理
            }
        }

        List<Paper> papers = searchMapper.getPaperByAuthor(author);
        if (papers.isEmpty()) {
            return CommonResponse.createForError("没有找到匹配的论文，请尝试其他作者字段");
        }

        try {
            String jsonData = objectMapper.writeValueAsString(papers);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return CommonResponse.createForSuccess(papers);
    }


    //按照文章摘要搜索
    public CommonResponse<List<Paper>> getPaperByAbstract(String Abstract) {
        String cacheKey = "paperSearch:" + Abstract;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                List<Paper> cachedData = objectMapper.readValue(cacheValue, new TypeReference<List<Paper>>() {
                });
                return CommonResponse.createForSuccess(cachedData);
            } catch (IOException e) {
                e.printStackTrace(); // JSON 解析错误处理
            }
        }

        List<Paper> papers = searchMapper.getPaperByAbstract(Abstract);
        if (papers.isEmpty()) {
            return CommonResponse.createForError("没有找到匹配的论文，请尝试其他作者字段");
        }

        try {
            String jsonData = objectMapper.writeValueAsString(papers);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return CommonResponse.createForSuccess(papers);
    }


    //按照关键词队列搜索
    public CommonResponse<List<Paper>> getPaperByQuery(String query) {
        String cacheKey = "paperSearch:" + query;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                List<Paper> cachedData = objectMapper.readValue(cacheValue, new TypeReference<List<Paper>>() {
                });
                return CommonResponse.createForSuccess(cachedData);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        List<Paper> papers = searchMapper.getPaperByQuery(query);
        if (papers.isEmpty()) {
            return CommonResponse.createForError("没有找到匹配的信息，请尝试其他搜索信息");
        }

        try {
            String jsonData = objectMapper.writeValueAsString(papers);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return CommonResponse.createForSuccess(papers);
    }

}
