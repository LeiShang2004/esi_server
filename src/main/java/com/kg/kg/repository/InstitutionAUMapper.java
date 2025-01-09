package com.kg.kg.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.kg.entities.InstitutionAU;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstitutionAUMapper extends BaseMapper<InstitutionAU> {
    @Select("SELECT * FROM ${tableName} WHERE InstitutionName = #{institutionName} AND SC = 'total'")
    List<InstitutionAU> findByInstitutionName(@Param("tableName") String tableName, @Param("institutionName") String institutionName);

    @Select("SELECT * FROM ${tableName} WHERE InstitutionName = #{institutionName} AND SC = #{sc}")
    List<InstitutionAU> findByInstitutionNameAndSc(@Param("tableName") String tableName, @Param("institutionName") String institutionName, @Param("sc") String sc);
}
