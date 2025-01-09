package com.kg.kg.controller;


import com.kg.kg.common.CommonResponse;
import com.kg.kg.common.CommonResponseMutilType;
import com.kg.kg.entities.InstitutionAU;
import com.kg.kg.entities.InstitutionTotal;
import com.kg.kg.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/Institutions")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;


    @GetMapping("/InstitutionList")
    @ResponseBody
    public CommonResponse<List<String>> getInstitution(@RequestParam String institutionName) {
        return institutionService.selectInstitution(institutionName);
    }

    @GetMapping("/InstitutionMessage")
    @ResponseBody
    public CommonResponseMutilType<InstitutionTotal, List<String>> getInstitutionMessage(@RequestParam String institutionName) {
        CommonResponseMutilType<InstitutionTotal, List<String>> result = institutionService.getInstitutionMessage(institutionName);
        return result;
    }

    @GetMapping("/InstitutionTotalMessage")
    @ResponseBody
    public CommonResponse<List<InstitutionTotal>> getInstitutionTotalMessage(@RequestParam String institutionName) {
        CommonResponse<List<InstitutionTotal>> result = institutionService.getInstitutionData(institutionName);
        return result;
    }

    @GetMapping("/SCMessage")
    @ResponseBody
    public CommonResponse<InstitutionTotal> getSCMessage(@RequestParam String institutionName, @RequestParam String SC) {
        CommonResponse<InstitutionTotal> result = institutionService.getInstitutionbySC(institutionName, SC);
        return result;
    }

    @GetMapping("/AUMessage")
    @ResponseBody
    public CommonResponse<List<InstitutionAU>> getAUMessage(@RequestParam String institutionName) {
        CommonResponse<List<InstitutionAU>> result = institutionService.selectInstitutionAU(institutionName);
        return result;
    }

    @GetMapping("/AUMessagebySC")
    @ResponseBody
    public CommonResponse<List<InstitutionAU>> getAUMessagebySC(@RequestParam String institutionName, @RequestParam String SC) {
        CommonResponse<List<InstitutionAU>> result = institutionService.selectInstitutionAUbySC(institutionName, SC);
        return result;
    }
}
