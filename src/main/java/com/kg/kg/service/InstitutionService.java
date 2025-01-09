package com.kg.kg.service;

import com.kg.kg.common.CommonResponse;
import com.kg.kg.common.CommonResponseMutilType;
import com.kg.kg.entities.InstitutionAU;
import com.kg.kg.entities.InstitutionTotal;

import java.util.List;

public interface InstitutionService {

    String selectTable(String InstitutionName);

    String selectTableTotal(String InstitutionName);

    String selectTableAU(String InstitutionName);

    CommonResponse<List<String>> selectInstitution(String InstitutionName);

    CommonResponseMutilType<InstitutionTotal, List<String>> getInstitutionMessage(String InstitutionName);

    CommonResponse<List<InstitutionTotal>> getInstitutionData(String InstitutionName);

    CommonResponse<InstitutionTotal> getInstitutionbySC(String InstitutionName, String SC);

    CommonResponse<List<InstitutionAU>> selectInstitutionAU(String InstitutionName);

    CommonResponse<List<InstitutionAU>> selectInstitutionAUbySC(String InstitutionName, String SC);
}
