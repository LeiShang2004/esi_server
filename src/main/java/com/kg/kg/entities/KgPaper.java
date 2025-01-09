package com.kg.kg.entities;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KgPaper {
    // 论文id
    private String id;
    // 论文标题
    private String ti;
    // 论文作者
    private String au;
    // 论文摘要
    private String ab;
    // 论文关键词
    private String z9;
    // 论文出版年份
    private String py;
    // 论文出版期刊
    private String wc;
    // 论文引用次数
    private String so;
    // 论文被引用次数
    private String cr;

    @Setter
    @Getter
    // 论文引用的论文id
    private List<String> citedPapersIds;

}
