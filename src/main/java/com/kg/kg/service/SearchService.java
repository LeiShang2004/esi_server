package com.kg.kg.service;

import com.kg.kg.common.CommonResponse;
import com.kg.kg.entities.Paper;

import java.util.List;

public interface SearchService {

    CommonResponse<List<Paper>> getPaperByTitle(String Title);

    CommonResponse<List<Paper>> getPaperByAuthor(String Author);

    CommonResponse<List<Paper>> getPaperByAbstract(String Abstract);

    CommonResponse<List<Paper>> getPaperByQuery(String Query);

}
