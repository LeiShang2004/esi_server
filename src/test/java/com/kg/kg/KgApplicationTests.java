package com.kg.kg;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.IndicesResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kg.kg.entities.KgPaper;
import com.kg.kg.entities.Paper;
import com.kg.kg.repository.KgPaperMapper;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class KgApplicationTests {

    @Test
    void test() throws IOException {
        String serverUrl = "http://localhost:9200";

// Create the low-level client
        RestClient restClient = RestClient.builder(HttpHost.create(serverUrl)).build();

// Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

// And create the API client
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

// Search for papers with "deep" in the author field
        SearchResponse<KgPaper> response = esClient.search(s -> s
                        .index("papers")
                        .query(q -> q
                                .match(m -> m
                                        .field("au")
                                        .query("Yoshua")
                                )
                        ),
                KgPaper.class
        );

// Print the papers
        List<Hit<KgPaper>> hits = response.hits().hits();
        System.out.println(hits);
        List<KgPaper> papers = hits.stream().map(Hit::source).collect(Collectors.toList());
        System.out.println(papers);
    }


//    @Autowired
//    ElasticsearchClient client;
//
//    @Autowired
//    private KgPaperMapper kgPaperMapper;
//
//
//    //创建索引
//    @Test
//    void createIndex() throws IOException {
//        CreateIndexResponse indexResponse = client.indices().create(c -> c.index("papers"));
//        System.out.println(indexResponse);
//        System.out.println(indexResponse.acknowledged());
//    }
//
//    //判断索引是否存在
//    @Test
//    void existsIndex() throws IOException {
//        BooleanResponse booleanResponse = client.indices().exists(e -> e.index("papers"));
//        System.out.println(booleanResponse.value());
//    }
//
//    //查询某个索引信息
//    @Test
//    void findIndex() throws IOException {
//        GetIndexResponse getIndexResponse = client.indices().get(getIndex -> getIndex.index("papers"));
//        System.out.println(getIndexResponse.result());
//    }
//
//    //查询所有索引
//    @Test
//    void findIndice() throws IOException {
//        IndicesResponse indicesResponse = client.cat().indices();
//        System.out.println(indicesResponse.valueBody());
//    }
//
//    //删除某个索引
//    @Test
//    void deleteIndex() throws IOException {
//        DeleteIndexResponse deleteIndexResponse = client.indices().delete(d -> d.index("papers"));
//        System.out.println(deleteIndexResponse.acknowledged());
//    }
//
//    //添加文档
//    @Test
//    void addDocument() throws IOException {
//        List<String> citedpaperids = new ArrayList<>(1);
//        KgPaper kgPaper = new KgPaper("1", "machine learning", "wyr", "something...", "5", "pytest", "computer", "sotest", "wyr's books", citedpaperids);
//        IndexResponse indexResponse = client.index(i -> i
//                .index("papers")
//                .id(String.valueOf(kgPaper.getId()))
//                .document(kgPaper));
//        System.out.println(indexResponse);
//    }
//
//    //批量提取数据
//    @Test
//    void testTransferDataToElasticsearch() throws IOException {
//        // 从 MySQL 中查询所有数据
//        List<Paper> kgPapers = kgPaperMapper.selectList(Wrappers.<Paper>query());
//
//        // 将数据插入到 Elasticsearch
//        for (Paper kgPaper : kgPapers) {
//            IndexResponse indexResponse = client.index(i -> i
//                    .index("papers")
//                    .id(String.valueOf(kgPaper.getID()))
//                    .document(kgPaper));
//            System.out.println(indexResponse);
//        }
//    }
}
