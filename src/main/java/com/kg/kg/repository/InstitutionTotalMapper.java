package com.kg.kg.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.kg.entities.InstitutionTotal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstitutionTotalMapper extends BaseMapper<InstitutionTotal> {
    @Select("SELECT * FROM ${tableName} WHERE InstitutionName = #{institutionName} AND SC = 'total'")
    InstitutionTotal selectTotalData(@Param("tableName") String tableName, @Param("institutionName") String institutionName);

    @Select("SELECT * FROM ${tableName} WHERE InstitutionName = #{institutionName} ")
    List<InstitutionTotal> selectInstitutionData(@Param("tableName") String tableName, @Param("institutionName") String institutionName);

    @Select("SELECT SC FROM ${tableName} WHERE InstitutionName = #{institutionName} ")
    List<String> selectSCData(@Param("tableName") String tableName, @Param("institutionName") String institutionName);

    @Select("SELECT * FROM ${tableName} WHERE InstitutionName = #{institutionName} AND SC = #{SC}")
    InstitutionTotal selectTotalSCData(@Param("tableName") String tableName, @Param("institutionName") String institutionName, @Param("SC") String SC);
}