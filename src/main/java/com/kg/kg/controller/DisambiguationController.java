package com.kg.kg.controller;


import com.kg.kg.entities.DisambiguationI;
import com.kg.kg.service.DisambiguationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kg.kg.common.CommonResponse;
import com.kg.kg.common.CommonResponseMutilType;
import com.kg.kg.entities.Disambiguation;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/Disambiguation")
public class DisambiguationController {

    @Autowired
    private DisambiguationService disambiguationService;

    @GetMapping("/Name")
    @ResponseBody
    public CommonResponse<List<Disambiguation>> getDisambiguationName(@RequestParam String name) {
        return disambiguationService.selectTableByName(name);
    }

//    @GetMapping("/SC")
//    @ResponseBody
//    public CommonResponse<List<Disambiguation>> getDisambiguationSC(@RequestParam String sc) {
//        return disambiguationService.selectTableBySC(sc);
//    }

    @GetMapping("/Institution")
    @ResponseBody
    public CommonResponse<List<DisambiguationI>> getDisambiguationInstitution(@RequestParam String institutionName) {
        return disambiguationService.selectTableByInstitution(institutionName);
    }

//    @GetMapping("/RI")
//    @ResponseBody
//    public CommonResponse<List<Disambiguation>> getDisambiguationRI(@RequestParam String RI) {
//        return disambiguationService.selectTableByRI(RI);
//    }
//
//    @GetMapping("/OI")
//    @ResponseBody
//    public CommonResponse<List<Disambiguation>> getDisambiguationOI(@RequestParam String OI) {
//        return disambiguationService.selectTableByOI(OI);
//    }
}
