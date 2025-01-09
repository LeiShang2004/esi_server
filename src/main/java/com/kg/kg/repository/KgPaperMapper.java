package com.kg.kg.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.kg.entities.Paper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KgPaperMapper extends BaseMapper<Paper> {
}
