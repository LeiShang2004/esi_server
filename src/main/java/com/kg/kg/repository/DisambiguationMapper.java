package com.kg.kg.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.kg.entities.Dis;
import com.kg.kg.entities.DisI;
import com.kg.kg.entities.Disambiguation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface DisambiguationMapper extends BaseMapper<Disambiguation> {
    @Select("SELECT\n" +
            "    o.author_id,\n" +
            "    GROUP_CONCAT(DISTINCT n.name SEPARATOR ';') AS all_names,\n" +
            "    GROUP_CONCAT(DISTINCT pro.professional_name SEPARATOR ';') AS research_direction,\n" +
            "    GROUP_CONCAT(DISTINCT iname.institution_name SEPARATOR ';') AS affiliated_institution_names,\n" +
            "    GROUP_CONCAT(DISTINCT i.institution_id SEPARATOR ';') AS affiliated_institution_ids,\n" +
            "    GROUP_CONCAT(DISTINCT\n" +
            "        CASE\n" +
            "            WHEN c.author1_id = a.author_id THEN c.author2_id\n" +
            "            ELSE c.author1_id\n" +
            "        END\n" +
            "        SEPARATOR ';'\n" +
            "    ) AS collaborator_ids,\n" +
            "    GROUP_CONCAT(DISTINCT c.paper_id SEPARATOR ';') AS collaboration_papers,\n" +
            "    GROUP_CONCAT(DISTINCT pap.paper_title SEPARATOR ';') AS paper_titles,\n" +
            "    GROUP_CONCAT(DISTINCT collab_name.name SEPARATOR ';') AS collaborator_names,\n" +
            "    RI,OI,\n" +
            "    GROUP_CONCAT(DISTINCT \n" +
            "        CASE \n" +
            "            WHEN c.author1_id = a.author_id THEN c.author2_id \n" +
            "            ELSE c.author1_id \n" +
            "        END \n" +
            "        SEPARATOR ';'\n" +
            "    ) AS collaborator_author_ids \n" +
            "FROM\n" +
            "    newesi.ownership o\n" +
            "JOIN\n" +
            "    newesi.name n ON o.name_id = n.name_id\n" +
            "JOIN\n" +
            "    newesi.author a ON o.author_id = a.author_id\n" +
            "LEFT JOIN\n" +
            "    newesi.involvement iv ON a.author_id = iv.author_id\n" +
            "LEFT JOIN\n" +
            "    newesi.professional pro ON iv.professional_id = pro.professional_id\n" +
            "LEFT JOIN\n" +
            "    newesi.affiliation af ON a.author_id = af.author_id\n" +
            "LEFT JOIN\n" +
            "    newesi.institution i ON af.institution_id = i.institution_id\n" +
            "LEFT JOIN\n" +
            "    newesi.institutionname iname ON i.institution_name_id = iname.institution_name_id\n" +
            "LEFT JOIN\n" +
            "    newesi.collaboration c ON a.author_id IN (c.author1_id, c.author2_id)\n" +
            "LEFT JOIN\n" +
            "    newesi.paper pap ON c.paper_id = pap.paper_id\n" +
            "LEFT JOIN\n" +
            "    newesi.collaboration collab ON a.author_id IN (collab.author1_id, collab.author2_id)\n" +
            "LEFT JOIN\n" +
            "    newesi.ownership collab_ownership ON\n" +
            "        (collab.author1_id = a.author_id AND collab_ownership.author_id = collab.author2_id) OR\n" +
            "        (collab.author2_id = a.author_id AND collab_ownership.author_id = collab.author1_id)\n" +
            "LEFT JOIN\n" +
            "    newesi.name collab_name ON collab_ownership.name_id = collab_name.name_id\n" +
            "WHERE\n" +
            "    a.author_id = #{id}\n" +
            "GROUP BY\n" +
            "    o.author_id;")
    List<Dis> selectListAu(@Param("id") int id);

    //机构id找寻作者实体
    @Select("SELECT\n" +
            "    i.institution_id,\n" +
            "    GROUP_CONCAT(DISTINCT o.author_id SEPARATOR ';') AS affiliated_author_ids,\n" +
            "    GROUP_CONCAT(DISTINCT n.name SEPARATOR ';') AS affiliated_all_names,\n" +
            "    GROUP_CONCAT(DISTINCT pro.professional_name SEPARATOR ';') AS research_direction,\n" +
            "    GROUP_CONCAT(DISTINCT iname.institution_name SEPARATOR ';') AS institution_names,\n" +
            "    GROUP_CONCAT(DISTINCT\n" +
            "        CASE\n" +
            "            WHEN c.author1_id = a.author_id THEN c.author2_id\n" +
            "            ELSE c.author1_id\n" +
            "        END\n" +
            "        SEPARATOR ';'\n" +
            "    ) AS collaborator_ids,\n" +
            "    GROUP_CONCAT(DISTINCT c.paper_id SEPARATOR ';') AS collaboration_papers,\n" +
            "    GROUP_CONCAT(DISTINCT pap.paper_title SEPARATOR ';') AS paper_titles,\n" +
            "    GROUP_CONCAT(DISTINCT collab_name.name SEPARATOR ';') AS collaborator_names,\n" +
            "    GROUP_CONCAT(DISTINCT \n" +
            "        CASE \n" +
            "            WHEN c.author1_id = a.author_id THEN c.author2_id \n" +
            "            ELSE c.author1_id \n" +
            "        END \n" +
            "        SEPARATOR ';'\n" +
            "    ) AS collaborator_author_ids \n" +
            "FROM\n" +
            "    newesi.ownership o\n" +
            "JOIN\n" +
            "    newesi.name n ON o.name_id = n.name_id\n" +
            "JOIN\n" +
            "    newesi.author a ON o.author_id = a.author_id\n" +
            "LEFT JOIN\n" +
            "    newesi.involvement iv ON a.author_id = iv.author_id\n" +
            "LEFT JOIN\n" +
            "    newesi.professional pro ON iv.professional_id = pro.professional_id\n" +
            "LEFT JOIN\n" +
            "    newesi.affiliation af ON a.author_id = af.author_id\n" +
            "LEFT JOIN\n" +
            "    newesi.institution i ON af.institution_id = i.institution_id\n" +
            "LEFT JOIN\n" +
            "    newesi.institutionname iname ON i.institution_name_id = iname.institution_name_id\n" +
            "LEFT JOIN\n" +
            "    newesi.collaboration c ON a.author_id IN (c.author1_id, c.author2_id)\n" +
            "LEFT JOIN\n" +
            "    newesi.paper pap ON c.paper_id = pap.paper_id\n" +
            "LEFT JOIN\n" +
            "    newesi.collaboration collab ON a.author_id IN (collab.author1_id, collab.author2_id)\n" +
            "LEFT JOIN\n" +
            "    newesi.ownership collab_ownership ON\n" +
            "        (collab.author1_id = a.author_id AND collab_ownership.author_id = collab.author2_id) OR\n" +
            "        (collab.author2_id = a.author_id AND collab_ownership.author_id = collab.author1_id)\n" +
            "LEFT JOIN\n" +
            "    newesi.name collab_name ON collab_ownership.name_id = collab_name.name_id\n" +
            "WHERE\n" +
            "    i.institution_id = #{institutionId}\n" +
            "GROUP BY\n" +
            "    i.institution_id;")
    List<DisI> selectListByInstitutionId(@Param("institutionId") int institutionId);


    //根据姓名找寻作者实体
    @Select("SELECT o.author_id\n" +
            "FROM newesi.name n\n" +
            "JOIN newesi.ownership o ON n.name_id = o.name_id\n" +
            "WHERE n.name = #{name};")
    List<Integer> selectIdByname(String name);

    //根据研究方向找寻作者实体
    @Select("SELECT i.author_id\n" +
            "FROM newesi.involvement i \n" +
            "JOIN newesi.professional p ON i.professional_id = p.professional_id\n" +
            "WHERE p.professional_name = #{sc};")
    List<Integer> selectIdBysc(String sc);

    //根据机构找寻作者实体
    @Select("SELECT i.institution_id\n" +
            "FROM newesi.institution i \n" +
            "JOIN newesi.institutionname iname ON i.institution_name_id = iname.institution_name_id\n" +
            "WHERE iname.institution_name = #{institutionName};")
    List<Integer> selectIdByInstitutionName(String institutionName);

    //根据RI找寻作者实体
    @Select("SELECT a.author_id\n" +
            "FROM newesi.author a\n" +
            "WHERE a.RI = #{RI};")
    List<Integer> selectIdByRI(String RI);

    //根据OI找寻作者实体
    @Select("SELECT a.author_id\n" +
            "FROM newesi.author a\n" +
            "WHERE a.OI = #{OI};")
    List<Integer> selectIdByOI(String OI);
}
