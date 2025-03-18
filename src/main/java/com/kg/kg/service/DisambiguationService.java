package com.kg.kg.service;

import com.kg.kg.common.CommonResponse;
import com.kg.kg.common.CommonResponseMutilType;
import com.kg.kg.entities.Disambiguation;

import java.util.List;

public interface DisambiguationService {
    //根据姓名找寻作者实体
    CommonResponse<List<Disambiguation>> selectTableByName(String name);

    CommonResponse<List<Disambiguation>> selectTableBySC(String sc);
    
    CommonResponse<List<Disambiguation>> selectTableByInstitution(String institutionName);


    CommonResponse<List<Disambiguation>> selectTableByRI(String ri);

    CommonResponse<List<Disambiguation>> selectTableByOI(String oi);
}
