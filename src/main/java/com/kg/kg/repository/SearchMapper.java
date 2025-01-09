package com.kg.kg.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import com.kg.kg.entities.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface SearchMapper extends BaseMapper<Paper> {

    //根据标题进行查询
    @Select("SELECT * FROM esi.paper WHERE paper.TI LIKE CONCAT(#{title}, '%')")
    List<Paper> getPaperByTitle(@Param("title") String title);

    //根据作者进行查询
    @Select("SELECT * FROM esi.paper WHERE paper.AU LIKE CONCAT(#{author}, '%')")
    List<Paper> getPaperByAuthor(@Param("author") String author);

    //根据摘要进行查询
    @Select("SELECT * FROM esi.paper WHERE paper.AB LIKE CONCAT( #{Abstract}, '%')")
    List<Paper> getPaperByAbstract(@Param("Abstract") String Abstract);

    //根据词序列组合搜索
    @Select("SELECT *, MATCH(TI) AGAINST (#{query} IN NATURAL LANGUAGE MODE) * 2 + " +
            "MATCH(AB) AGAINST (#{query} IN NATURAL LANGUAGE MODE) AS relevance " +
            "FROM esi.paper " +
            "WHERE MATCH(TI, AB) AGAINST (#{query} IN NATURAL LANGUAGE MODE) " +
            "ORDER BY relevance DESC")
    List<Paper> getPaperByQuery(@Param("query") String query);

//    SELECT *,
//    MATCH(TI) AGAINST (#{query}* IN NATURAL LANGUAGE MODE) AS ti_match,
//    MATCH(AB) AGAINST (#{query}* IN NATURAL LANGUAGE MODE) AS ab_match
//    FROM kg_graph_new.paper
//    WHERE MATCH(TI, AB) AGAINST (#{query}* IN NATURAL LANGUAGE MODE)
//    ORDER BY
//    CASE
//    WHEN TI = #{query} THEN 1
//    WHEN AB = #{query} THEN 2
//    ELSE 3
//    END,
//    ti_match DESC,
//    ab_match DESC;

//    SELECT *,
//    IF(TI = #{query}, 1, 0) AS ti_exact_match,
//    IF(AB = #{query}, 1, 0) AS ab_exact_match,
//    (MATCH(TI) AGAINST (#{query} IN NATURAL LANGUAGE MODE)) AS ti_fuzzy_relevance,
//    (MATCH(AB) AGAINST (#{query} IN NATURAL LANGUAGE MODE)) AS ab_fuzzy_relevance,
//    (MATCH(TI, AB) AGAINST (#{query} IN NATURAL LANGUAGE MODE)) AS combined_fuzzy_relevance
//    FROM kg_graph_new.paper
//    WHERE TI = #{query} OR AB = #{query} OR MATCH(TI, AB) AGAINST (#{query} IN NATURAL LANGUAGE MODE)
//    ORDER BY ti_exact_match DESC,
//    ab_exact_match DESC,
//    ti_fuzzy_relevance DESC,
//    ab_fuzzy_relevance DESC,
//    combined_fuzzy_relevance DESC;


}