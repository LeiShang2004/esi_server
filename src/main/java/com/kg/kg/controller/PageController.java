package com.kg.kg.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.kg.kg.entities.KgPaper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
//@RestController 用controller代替 仅返回页面 而不是json数据
@Controller
@RequestMapping(value = "/pages")
@CrossOrigin//跨域
public class PageController {

    @Resource
    private ElasticsearchClient client;


    /**
     * ES搜索
     *
     * @param query 查询关键字 标题、作者、摘要
     * @param model 传递数据到前端
     * @return 返回页面 page.html 模板引擎
     * @throws IOException IO异常
     */
    @GetMapping("/ES_search")
    public String search(@RequestParam String query, Model model) throws IOException {
        SearchResponse<KgPaper> response = client.search(s -> s
                        .index("papers")
                        .query(q -> q
                                .multiMatch(m -> m
                                        .fields("ti", "au", "ab")
                                        .query(query)
                                )
                        )
                        // 添加高亮配置
                        .highlight(h -> h
                                .fields("ti", f -> f)
                                .fields("au", f -> f)
                                .fields("ab", f -> f)
                                .preTags("<span style='color:red'>")
                                .postTags("</span>")
                        ),
                KgPaper.class
        );

        List<Hit<KgPaper>> hits = response.hits().hits();

        List<KgPaper> resultList = new ArrayList<>();
        for (Hit<KgPaper> hit : hits) {
            KgPaper kgPaper = hit.source();
            Map<String, List<String>> highlightFields = hit.highlight();
            if (highlightFields.containsKey("ti")) {
                kgPaper.setTi(String.join("", highlightFields.get("ti")));
            }
            if (highlightFields.containsKey("au")) {
                kgPaper.setAu(String.join("", highlightFields.get("au")));
            }
            if (highlightFields.containsKey("ab")) {
                kgPaper.setAb(String.join("", highlightFields.get("ab")));
            }
            resultList.add(kgPaper);
        }


        for (KgPaper kgPaper : resultList) {
            log.info("kgPaper: {}", kgPaper);
        }

        model.addAttribute("papers", resultList);
        return "page";

//        for (Hit<KgPaper> hit : hits) {
//            log.info("hit: {}", hit);
//        }
//        List<KgPaper> resultList = new ArrayList<>();
//        for (Hit<KgPaper> hit : hits) {
//            KgPaper kgPaper = hit.source();
//            Map<String, List<String>> highlightFields = hit.highlight().;
//            if (highlightFields.containsKey("ti")) {
//                if (kgPaper != null) {
//                    kgPaper.setTi(String.join("", highlightFields.get("ti")));
//                }
//            }
//            if (highlightFields.containsKey("au")) {
//                if (kgPaper != null) {
//                    kgPaper.setAu(String.join("", highlightFields.get("au")));
//                }
//            }
//            if (highlightFields.containsKey("ab")) {
//                if (kgPaper != null) {
//                    kgPaper.setAb(String.join("", highlightFields.get("ab")));
//                }
//            }
//            resultList.add(kgPaper);
//        }
    }
}
