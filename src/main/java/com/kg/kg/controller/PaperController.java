package com.kg.kg.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.kg.kg.common.LinkResponse;
import com.kg.kg.common.PaperResponse;
import com.kg.kg.common.searchResponse;
import com.kg.kg.entities.KgPaper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController

@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/Papers")

public class PaperController {

    @Resource
    private ElasticsearchClient client;

//    //按照标题，作者，摘要查询es中的paper
//    // 这得用模板引擎啊
//    @GetMapping("/ES_search")
//    public List<KgPaper> search(@RequestParam String query) throws IOException {
//        SearchResponse<KgPaper> response = client.search(s -> s
//                        .index("papers")
//                        .query(q -> q
//                                .multiMatch(m -> m
//                                        .fields("ti", "au", "ab")
//                                        .query(query)
//                                )
//                        ),
//                KgPaper.class
//        );
//
//        List<Hit<KgPaper>> hits = response.hits().hits();
//        return hits.stream()
//                .map(Hit::source)
//                .collect(Collectors.toList());
//    }

    //按照标题查询es中的paper
    @GetMapping("/ES_searchByTitle")
    public List<KgPaper> searchByTitle(@RequestParam String ti) throws IOException {
        SearchResponse<KgPaper> response = client.search(s -> s
                        .index("papers")
                        .query(q -> q
                                .match(m -> m
                                        .field("ti")
                                        .query(ti)
                                )
                        ),
                KgPaper.class
        );

        List<Hit<KgPaper>> hits = response.hits().hits();
        return hits.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    //按照作者查询es中的paper
    @GetMapping("/ES_searchByAuthor")
    public List<KgPaper> searchByAuthor(@RequestParam String au) throws IOException {
        SearchResponse<KgPaper> response = client.search(s -> s
                        .index("papers")
                        .query(q -> q
                                .match(m -> m
                                        .field("au")
                                        .query(au)
                                )
                        ),
                KgPaper.class
        );

        List<Hit<KgPaper>> hits = response.hits().hits();
        return hits.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    //按照摘要查询es中的paper
    @GetMapping("/ES_searchByAbstract")
    public List<KgPaper> searchByAbstract(@RequestParam String ab) throws IOException {
        SearchResponse<KgPaper> response = client.search(s -> s
                        .index("papers")
                        .query(q -> q
                                .match(m -> m
                                        .field("ab")
                                        .query(ab)
                                )
                        ),
                KgPaper.class
        );

        List<Hit<KgPaper>> hits = response.hits().hits();
        return hits.stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

//一跳查询处理方法如下

//    //按照标题查询es中的paper和被cited的内容，以及他们的关系link
//    @GetMapping("/SearchAndLinkByTi")
//    public searchResponse searchByTi(@RequestParam String query) throws IOException {
//        // 首先通过提供的标题字符串序列进行搜索
//        SearchResponse<KgPaper> response = client.search(s -> s
//                        .index("papers")
//                        .query(q -> q
//                                .match(m -> m
//                                        .field("ti")
//                                        .query(query)
//                                )
//                        ),
//                KgPaper.class
//        );
//
//        List<KgPaper> papers = response.hits().hits().stream()
//                .map(Hit::source)
//                .collect(Collectors.toList());
//
//        List<LinkResponse.Link> links = new ArrayList<>();
//
//        // 查询cited的paper的id，若该字段为空，那么调用查询方法处理
//        for (KgPaper paper : papers) {
//            if (paper.getCitedPapersIds() == null || paper.getCitedPapersIds().isEmpty()) {
//                if (paper.getCr() != null) {
//                    List<String> citedPaperIds = searchCitedPaperIds(paper.getCr());
//                    paper.setCitedPapersIds(citedPaperIds);
//                    // 查询后在es中的该字段进行写入
//                    updateCitedPapers(paper);
//                }
//            }
//            // 将每个关联的paper信息存入link中
//            for (String citedPaperId : paper.getCitedPapersIds()) {
//                links.add(new LinkResponse.Link(paper.getId(), citedPaperId));
//            }
//        }
//
//
//        PaperResponse paperResponse = new PaperResponse(papers);
//        LinkResponse linkResponse = new LinkResponse(links);
//
//        return new searchResponse(paperResponse, linkResponse);
//    }
//
//    //根据cr中的字段查找被引用的paper的id
//    private List<String> searchCitedPaperIds(String cr) {
//        List<String> citedPaperIds = new ArrayList<>();
//
//        // 根据分号分隔字段
//        String[] citedTitles = cr.split(";");
//
//        // Perform search for each cited reference
//        for (String citedTitle : citedTitles) {
//            List<String> ids = Arrays.stream(citedTitle.trim().split(","))
//                    .flatMap(citedTitlePart -> {
//                        try {
//                            SearchResponse<KgPaper> response = client.search(new SearchRequest.Builder()
//                                    .index("papers")
//                                    .query(new Query.Builder()
//                                            .term(new TermQuery.Builder()
//                                                    .field("ti.keyword")
//                                                    .value(citedTitlePart.trim())
//                                                    .build())
//                                            .build())
//                                    .source(source -> source.filter(f -> f.includes("id")))
//                                    .build(), KgPaper.class);
//
//                            return response.hits().hits().stream()
//                                    .map(Hit::source)
//                                    .map(KgPaper::getId); // 只获取该paper的id信息
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return null;
//                        }
//                    })
//                    .filter(id -> id != null)
//                    .collect(Collectors.toList());
//
//            citedPaperIds.addAll(ids);
//        }
//
//        return citedPaperIds;
//    }
//
//    //更新es中的cited字段，方便下次使用时查询
//    private void updateCitedPapers(KgPaper paper) throws IOException {
//        UpdateRequest<KgPaper, KgPaper> updateRequest = UpdateRequest.of(u -> u
//                .index("papers")
//                .id(paper.getId())
//                .doc(paper)
//        );
//        UpdateResponse<KgPaper> updateResponse = client.update(updateRequest, KgPaper.class);
//        System.out.println("Update response result: " + updateResponse.result());
//    }

    //二跳查询处理方法
    // 按标题搜索，并根据引文链接论文
    @GetMapping("/SearchAndLinkByTi")
    public searchResponse searchByTi(@RequestParam String query) throws IOException {
        // First search by the provided title query
        SearchResponse<KgPaper> response = client.search(s -> s
                        .index("papers")
                        .query(q -> q
                                .match(m -> m
                                        .field("ti")
                                        .query(query)
                                )
                        ),
                KgPaper.class
        );

        List<KgPaper> papers = response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        List<LinkResponse.Link> links = new ArrayList<>();
        Set<String> processedIds = new HashSet<>();
        List<KgPaper> allPapers = new ArrayList<>();

        // Process each paper and its citations recursively
        for (KgPaper paper : papers) {
            processedIds.add(paper.getId());
            processCitedPapers(paper, links, processedIds, 2, allPapers);
        }

        PaperResponse paperResponse = new PaperResponse(allPapers);
        LinkResponse linkResponse = new LinkResponse(links);

        return new searchResponse(paperResponse, linkResponse);
    }

    private void processCitedPapers(KgPaper paper, List<LinkResponse.Link> links, Set<String> processedIds, int depth, List<KgPaper> allPapers) throws IOException {
        if (depth == 0 || (paper.getCitedPapersIds() == null && paper.getCr() == null)) {
            return;
        }

        if (paper.getCitedPapersIds() == null || paper.getCitedPapersIds().isEmpty()) {
            List<String> citedPaperIds = searchCitedPaperIds(paper.getCr());
            paper.setCitedPapersIds(citedPaperIds);
            updateCitedPapers(paper);
        }

        for (String citedPaperId : paper.getCitedPapersIds()) {
            links.add(new LinkResponse.Link(paper.getId(), citedPaperId));
            if (!processedIds.contains(citedPaperId)) {
                processedIds.add(citedPaperId);
                KgPaper citedPaper = getPaperById(citedPaperId);
                if (citedPaper != null) {
                    allPapers.add(citedPaper); // Add the cited paper to allPapers if not already present
                    processCitedPapers(citedPaper, links, processedIds, depth - 1, allPapers);
                }
            }
        }

        // Add the current paper to allPapers if not already present
        if (!allPapers.contains(paper)) {
            allPapers.add(paper);
        }
    }

    private List<String> searchCitedPaperIds(String cr) throws IOException {
        List<String> citedPaperIds = new ArrayList<>();
        String[] citedTitles = cr.split(";");

        for (String citedTitle : citedTitles) {
            List<String> ids = Arrays.stream(citedTitle.trim().split(","))
                    .flatMap(citedTitlePart -> {
                        try {
                            SearchResponse<KgPaper> response = client.search(s -> s
                                            .index("papers")
                                            .query(q -> q
                                                    .term(t -> t
                                                            .field("ti.keyword")
                                                            .value(citedTitlePart.trim())
                                                    )
                                            )
                                            .source(src -> src.filter(f -> f.includes("id"))),
                                    KgPaper.class
                            );

                            return response.hits().hits().stream()
                                    .map(Hit::source)
                                    .map(KgPaper::getId);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            citedPaperIds.addAll(ids);
        }

        return citedPaperIds;
    }

    private KgPaper getPaperById(String paperId) throws IOException {
        GetResponse<KgPaper> getResponse = client.get(g -> g
                        .index("papers")
                        .id(paperId),
                KgPaper.class
        );
        return getResponse.source();
    }

    private void updateCitedPapers(KgPaper paper) throws IOException {
        client.update(u -> u
                        .index("papers")
                        .id(paper.getId())
                        .doc(paper),
                KgPaper.class
        );
    }

// Classes for PaperResponse, LinkResponse, and SearchResponse


}


