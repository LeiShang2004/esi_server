package com.kg.kg.service.impl;

import com.kg.kg.common.CommonResponse;
import com.kg.kg.common.CommonResponseMutilType;
import com.kg.kg.entities.Disambiguation;
import com.kg.kg.repository.DisambiguationMapper;
import com.kg.kg.service.DisambiguationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("DisambiguationService")
public class DisambiguationServiceImpl implements DisambiguationService {

    @Autowired
    private DisambiguationMapper disambiguationMapper;

    @Override
    public CommonResponse<List<Disambiguation>> selectTableByName(String name) {
        List<Integer> ids = disambiguationMapper.selectIdByname(name);
        if (ids.isEmpty()) {
            return CommonResponse.createForError("没有");
        }
        List<Disambiguation> disambiguation = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            disambiguation.addAll(disambiguationMapper.selectList(ids.get(i)));
        }
        return CommonResponse.createForSuccess(disambiguation);
    }

    @Override
    public CommonResponse<List<Disambiguation>> selectTableBySC(String sc) {
        List<Integer> ids = disambiguationMapper.selectIdBysc(sc);
        if (ids.isEmpty()) {
            return CommonResponse.createForError("没有");
        }
        List<Disambiguation> disambiguation = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            disambiguation.addAll(disambiguationMapper.selectList(ids.get(i)));
        }
        return CommonResponse.createForSuccess(disambiguation);
    }

    @Override
    public CommonResponse<List<Disambiguation>> selectTableByInstitution(String institutionName) {
        List<Integer> ids = disambiguationMapper.selectIdByInstitutionName(institutionName);
        if (ids.isEmpty()) {
            return CommonResponse.createForError("没有");
        }
        List<Disambiguation> disambiguation = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            disambiguation.addAll(disambiguationMapper.selectList(ids.get(i)));
        }
        return CommonResponse.createForSuccess(disambiguation);
    }

    @Override
    public CommonResponse<List<Disambiguation>> selectTableByRI(String ri) {
        List<Integer> ids = disambiguationMapper.selectIdByRI(ri);
        if (ids.isEmpty()) {
            return CommonResponse.createForError("没有");
        }
        List<Disambiguation> disambiguation = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            disambiguation.addAll(disambiguationMapper.selectList(ids.get(i)));
        }
        return CommonResponse.createForSuccess(disambiguation);
    }

    @Override
    public CommonResponse<List<Disambiguation>> selectTableByOI(String oi) {
        List<Integer> ids = disambiguationMapper.selectIdByOI(oi);
        if (ids.isEmpty()) {
            return CommonResponse.createForError("没有");
        }
        List<Disambiguation> disambiguation = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            disambiguation.addAll(disambiguationMapper.selectList(ids.get(i)));
        }
        return CommonResponse.createForSuccess(disambiguation);
    }

}
