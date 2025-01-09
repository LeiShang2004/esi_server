package com.kg.kg.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.kg.entities.InstitutionData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstitutionMapper extends BaseMapper<InstitutionData> {
    @Select("SELECT DISTINCT InstitutionName FROM ${tableName} WHERE InstitutionName LIKE CONCAT(#{institutionName}, '%')")
    List<String> selectInstitutionName(@Param("tableName") String tableName, @Param("institutionName") String institutionName);

}
