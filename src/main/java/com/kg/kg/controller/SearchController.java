package com.kg.kg.controller;

import com.kg.kg.common.CommonResponse;
import com.kg.kg.entities.Paper;
import com.kg.kg.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/Papers")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/SearchByTitle")
    @ResponseBody
    public CommonResponse<List<Paper>> searchPapersByTitle(@RequestParam String title) {
        CommonResponse<List<Paper>> result = searchService.getPaperByTitle(title);
        return result;
    }

    @GetMapping("/SearchByAuthor")
    @ResponseBody
    public CommonResponse<List<Paper>> searchPapersByAuthor(@RequestParam String author) {
        CommonResponse<List<Paper>> result = searchService.getPaperByAuthor(author);
        return result;
    }

    @GetMapping("/SearchByAbstract")
    @ResponseBody
    public CommonResponse<List<Paper>> searchPapersByAbstract(@RequestParam String Abstract) {
        CommonResponse<List<Paper>> result = searchService.getPaperByAbstract(Abstract);
        return result;
    }

    @GetMapping("/SearchByQuery")
    @ResponseBody
    public CommonResponse<List<Paper>> searchPapersByQuery(@RequestParam String query) {
        CommonResponse<List<Paper>> result = searchService.getPaperByQuery(query);
        return result;
    }
}
