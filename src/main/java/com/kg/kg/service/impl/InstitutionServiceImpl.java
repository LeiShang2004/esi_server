package com.kg.kg.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.kg.common.CommonResponse;
import com.kg.kg.common.CommonResponseMutilType;
import com.kg.kg.entities.InstitutionAU;
import com.kg.kg.entities.InstitutionTotal;
import com.kg.kg.repository.InstitutionAUMapper;
import com.kg.kg.repository.InstitutionMapper;
import com.kg.kg.repository.InstitutionTotalMapper;
import com.kg.kg.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service("InstitutionService")
public class InstitutionServiceImpl implements InstitutionService {
    @Autowired
    private InstitutionMapper institutionMapper;

    @Autowired
    private InstitutionTotalMapper institutionTotalMapper;

    @Autowired
    private InstitutionAUMapper institutionAUMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    //查找机构所在表
    public String selectTable(String InstitutionName) {
        if (InstitutionName.length() < 2) {
            return "请输入更多字母来获取信息";
        } else {
            char firstLetter = Character.toLowerCase(InstitutionName.charAt(0));
            String tableName;
            if (firstLetter <= 'z' && firstLetter >= 'a') {
                tableName = firstLetter + "_institution";
            } else {
                tableName = "other_institution";
            }
            return tableName;
        }
    }

    public String selectTableTotal(String InstitutionName) {
        if (InstitutionName.length() == 0) {
            return "请输入准确的机构名";
        } else {
            char firstLetter = Character.toLowerCase(InstitutionName.charAt(0));
            String tableName;
            if (firstLetter <= 'z' && firstLetter >= 'a') {
                tableName = firstLetter + "_institutiontotal";
            } else {
                tableName = "other_institutiontotal";
            }
            return tableName;
        }
    }

    // todo: 不好说
    public String selectTableAU(String InstitutionName) {
        if (InstitutionName.length() == 0) {
            return "请输入准确的机构名";
        } else {
            char firstLetter = Character.toLowerCase(InstitutionName.charAt(0));
            String tableName;
            if (firstLetter <= 'z' && firstLetter >= 'a') {
                tableName = firstLetter + "_institutionau";
            } else {
                tableName = "other_institutionau";
            }
            return tableName;
        }
    }

    //根据已给的字母返回可能的机构名
    public CommonResponse<List<String>> selectInstitution(String institutionName) {
        String cacheKey = "selectInstitution:" + institutionName;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                List<String> cachedData = objectMapper.readValue(cacheValue, new TypeReference<List<String>>() {
                });
                return CommonResponse.createForSuccess(cachedData);
            } catch (IOException e) {
                e.printStackTrace();
                // 处理 JSON 解析错误
            }
        }

        String tableName = selectTable(institutionName);
        if ("请输入更多字母来获取信息".equals(tableName)) {
            return CommonResponse.createForError("请输入更多字母来获取信息");
        }

        List<String> institutionData = institutionMapper.selectInstitutionName(tableName, institutionName);
        if (institutionData.isEmpty()) {
            return CommonResponse.createForError("机构不存在，请搜索其他机构");
        }

        List<String> sortedInstitutionData = institutionData.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        try {
            String jsonData = objectMapper.writeValueAsString(sortedInstitutionData);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 处理 JSON 序列化错误
        }

        return CommonResponse.createForSuccess(sortedInstitutionData);
    }

    // 根据选择的机构返回机构所有信息
    public CommonResponse<List<InstitutionTotal>> getInstitutionData(String InstitutionName) {
        String cacheKey = "institutionData:" + InstitutionName;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                List<InstitutionTotal> cachedData = objectMapper.readValue(cacheValue, new TypeReference<List<InstitutionTotal>>() {
                });
                return CommonResponse.createForSuccess(cachedData);
            } catch (IOException e) {
                e.printStackTrace(); // JSON 解析错误处理
            }
        }

        String tableName = selectTableTotal(InstitutionName);
        if ("请输入准确的机构名".equals(tableName)) {
            return CommonResponse.createForError("请输入准确的机构名");
        }

        List<InstitutionTotal> institutionTotals = institutionTotalMapper.selectInstitutionData(tableName, InstitutionName);
        if (institutionTotals.isEmpty()) {
            return CommonResponse.createForError("机构不存在，请搜索其他机构");
        }

        try {
            String jsonData = objectMapper.writeValueAsString(institutionTotals);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return CommonResponse.createForSuccess(institutionTotals);
    }

    // 根据选择的机构返回机构包含的学科和总发文量
    public CommonResponseMutilType<InstitutionTotal, List<String>> getInstitutionMessage(String institutionName) {
        String cacheKey = "institutionMessage:" + institutionName;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);
        if (cacheValue != null) {
            try {
                CommonResponseMutilType<InstitutionTotal, List<String>> cachedData = objectMapper.readValue(cacheValue, new TypeReference<CommonResponseMutilType<InstitutionTotal, List<String>>>() {
                });
                return cachedData;
            } catch (IOException e) {
                e.printStackTrace(); // JSON 解析错误处理
            }
        }

        String tableName = selectTableTotal(institutionName);
        if ("请输入准确的机构名".equals(tableName)) {
            return CommonResponseMutilType.createForError("请输入准确的机构名");
        }

        InstitutionTotal institutionTotal = institutionTotalMapper.selectTotalData(tableName, institutionName);
        List<String> SC = institutionTotalMapper.selectSCData(tableName, institutionName);
        if (institutionTotal == null || SC.isEmpty()) {
            return CommonResponseMutilType.createForError("机构不存在，请搜索其他机构");
        }

        CommonResponseMutilType<InstitutionTotal, List<String>> response = CommonResponseMutilType.createForSuccess(institutionTotal, SC);
        try {
            String jsonData = objectMapper.writeValueAsString(response);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return response;
    }

    //根据选择的机构和学科返回学科每年发文
    public CommonResponse<InstitutionTotal> getInstitutionbySC(String InstitutionName, String SC) {
        String cacheKey = "institutionTotal:" + InstitutionName + ":" + SC;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                InstitutionTotal cachedInstitutionTotal = objectMapper.readValue(cacheValue, InstitutionTotal.class);
                return CommonResponse.createForSuccess(cachedInstitutionTotal);
            } catch (IOException e) {
                e.printStackTrace(); // JSON 解析错误处理
            }
        }

        String tableName = selectTableTotal(InstitutionName);
        if ("请输入准确的机构名".equals(tableName)) {
            return CommonResponse.createForError("请输入准确的机构名");
        }

        InstitutionTotal institutionTotal = institutionTotalMapper.selectTotalSCData(tableName, InstitutionName, SC);
        if (institutionTotal == null) {
            return CommonResponse.createForError("请选择准确的机构与学科名");
        }

        try {
            String jsonData = objectMapper.writeValueAsString(institutionTotal);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return CommonResponse.createForSuccess(institutionTotal);
    }

    // 根据选择的机构返回机构所有信息
    public CommonResponse<List<InstitutionAU>> selectInstitutionAU(String InstitutionName) {
        String cacheKey = "institutionAU:" + InstitutionName;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                List<InstitutionAU> cachedData = objectMapper.readValue(cacheValue, new TypeReference<List<InstitutionAU>>() {
                });
                return CommonResponse.createForSuccess(cachedData);
            } catch (IOException e) {
                e.printStackTrace(); // JSON 解析错误处理
            }
        }

        String tableName = selectTableAU(InstitutionName);
        if ("请输入准确的机构名".equals(tableName)) {
            return CommonResponse.createForError("请输入准确的机构名");
        }

        List<InstitutionAU> institutionAUS = institutionAUMapper.findByInstitutionName(tableName, InstitutionName);
        List<InstitutionAU> sortedInstitutionAUS = institutionAUS.stream()
                .sorted(Comparator.comparingInt(InstitutionAU::getPaperNumber).reversed())
                .collect(Collectors.toList());

        if (sortedInstitutionAUS.isEmpty()) {
            return CommonResponse.createForError("请输入正确的机构名");
        }

        try {
            String jsonData = objectMapper.writeValueAsString(sortedInstitutionAUS);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return CommonResponse.createForSuccess(sortedInstitutionAUS);
    }

    // 根据选择的机构和学科返回包含的作者
    public CommonResponse<List<InstitutionAU>> selectInstitutionAUbySC(String InstitutionName, String SC) {
        String cacheKey = "institutionAU:" + InstitutionName + ":" + SC;
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            try {
                List<InstitutionAU> cachedData = objectMapper.readValue(cacheValue, new TypeReference<List<InstitutionAU>>() {
                });
                return CommonResponse.createForSuccess(cachedData);
            } catch (IOException e) {
                e.printStackTrace(); // JSON 解析错误处理
            }
        }

        String tableName = selectTableAU(InstitutionName);
        if ("请输入准确的机构名".equals(tableName)) {
            return CommonResponse.createForError("请输入准确的机构名");
        }

        List<InstitutionAU> institutionAUS = institutionAUMapper.findByInstitutionNameAndSc(tableName, InstitutionName, SC);
        List<InstitutionAU> sortedInstitutionAUS = institutionAUS.stream()
                .sorted(Comparator.comparingInt(InstitutionAU::getPaperNumber).reversed())
                .collect(Collectors.toList());

        if (sortedInstitutionAUS.isEmpty()) {
            return CommonResponse.createForError("请选择正确的机构与学科名");
        }

        try {
            String jsonData = objectMapper.writeValueAsString(sortedInstitutionAUS);
            redisTemplate.opsForValue().set(cacheKey, jsonData, Duration.ofHours(1)); // 缓存结果1小时
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 序列化错误处理
        }

        return CommonResponse.createForSuccess(sortedInstitutionAUS);
    }
}
