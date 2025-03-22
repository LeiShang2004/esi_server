package com.kg.kg.service.impl;

import com.kg.kg.common.CommonResponse;
import com.kg.kg.entities.DisI;
import com.kg.kg.entities.Disambiguation;
import com.kg.kg.entities.Dis;
import com.kg.kg.entities.DisambiguationI;
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
            List<Dis> dis = disambiguationMapper.selectListAu(ids.get(i));
            disambiguation.addAll(datacope(dis,disambiguation));

        }
        return CommonResponse.createForSuccess(disambiguation);
    }

    private List<Disambiguation> datacope(List<Dis> dis, List<Disambiguation> disambiguation) {
        List<Disambiguation> disambiguationList = new ArrayList<>();
        for (Dis x : dis) {
            System.out.println(x);
            Disambiguation newDisambiguation = new Disambiguation();
            newDisambiguation.setId(x.getAuthorId());
            //Dis的name是一个字符串，我们要按分号分割传入Disambiguation
            newDisambiguation.setName(x.getAllNames().split(";"));
            if (x.getAffiliatedInstitutionNames() == null) {
                newDisambiguation.setInstitutions(null);
            }else {
                newDisambiguation.setInstitutions(x.getAffiliatedInstitutionNames().split(";"));
            }
            if (x.getResearchDirection() == null) {
                newDisambiguation.setSc(null);
            }else {
                newDisambiguation.setSc(x.getResearchDirection().split(";"));
            }
            //有可能是null,所以要判断
            if (x.getCollaborationPapers() == null) {
                newDisambiguation.setPaperId(null);
            } else {
                newDisambiguation.setPaperId(x.getCollaborationPapers().split(";"));
            }
            //同理
            if (x.getPaperTitles() == null) {
                newDisambiguation.setPaper(null);
            } else {
                newDisambiguation.setPaper(x.getPaperTitles().split(";"));
            }
            //同理
            if (x.getCollaboratorNames() == null) {
                newDisambiguation.setCoauthor(null);
            } else {
                newDisambiguation.setCoauthor(x.getCollaboratorNames().split(";"));
            }
            //同理
            if (x.getCollaboratorAuthorIds() == null) {
                newDisambiguation.setCollaborator_author_ids(null);
            } else {
                newDisambiguation.setCollaborator_author_ids(x.getCollaboratorAuthorIds().split(";"));
            }
            newDisambiguation.setRI(x.getRI()==null?"":x.getRI());
            newDisambiguation.setOI(x.getOI()==null?"":x.getOI());
            disambiguationList.add(newDisambiguation);
        }
        return disambiguationList;
    }

    public List<DisambiguationI> datacope2(List<DisI> disi, List<DisambiguationI> disambiguationI) {
        List<DisambiguationI> disambiguationIList = new ArrayList<>();
        for (DisI x : disi) {
            DisambiguationI newDisambiguationI = new DisambiguationI();
            if (x.getAffiliatedAllNames() != null) {
                newDisambiguationI.setName(x.getAffiliatedAllNames().split(";"));
            } else {
                newDisambiguationI.setName(new String[0]);
            }

            if (x.getInstitutionNames() != null) {
                newDisambiguationI.setInstitutionNames(x.getInstitutionNames().split(";"));
            } else {
                newDisambiguationI.setInstitutionNames(new String[0]);
            }

            newDisambiguationI.setInstitutionId(x.getInstitutionId());

            if (x.getResearchDirection() != null) {
                newDisambiguationI.setSc(x.getResearchDirection().split(";"));
            } else {
                newDisambiguationI.setSc(new String[0]);
            }

            if (x.getCollaborationPapers() != null) {
                newDisambiguationI.setPaperId(x.getCollaborationPapers().split(";"));
            } else {
                newDisambiguationI.setPaperId(new String[0]);
            }

            if (x.getPaperTitles() != null) {
                newDisambiguationI.setPaper(x.getPaperTitles().split(";"));
            } else {
                newDisambiguationI.setPaper(new String[0]);
            }

            if (x.getCollaboratorNames() != null) {
                newDisambiguationI.setCoauthor(x.getCollaboratorNames().split(";"));
            } else {
                newDisambiguationI.setCoauthor(new String[0]);
            }

            if (x.getCollaboratorAuthorIds() != null) {
                newDisambiguationI.setCollaborator_author_ids(x.getCollaboratorAuthorIds().split(";"));
            } else {
                newDisambiguationI.setCollaborator_author_ids(new String[0]);
            }
            disambiguationIList.add(newDisambiguationI);
        }
        return disambiguationIList;
    }

//    @Override
//    public CommonResponse<List<Disambiguation>> selectTableBySC(String sc) {
//        List<Integer> ids = disambiguationMapper.selectIdBysc(sc);
//        if (ids.isEmpty()) {
//            return CommonResponse.createForError("没有");
//        }
//        List<Disambiguation> disambiguation = new ArrayList<>();
//        for (int i = 0; i < ids.size(); i++) {
//            List<Dis> dis = disambiguationMapper.selectListAu(ids.get(i));
//            disambiguation.addAll(datacope(dis,disambiguation));
//        }
//        return CommonResponse.createForSuccess(disambiguation);
//    }

    @Override
    public CommonResponse<List<DisambiguationI>> selectTableByInstitution(String institutionName) {
        List<Integer> ids = disambiguationMapper.selectIdByInstitutionName(institutionName);
        if (ids.isEmpty()) {
            return CommonResponse.createForError("没有");
        }
        List<DisambiguationI> disambiguationi = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            List<DisI> disi = disambiguationMapper.selectListByInstitutionId(ids.get(i));
            disambiguationi.addAll(datacope2(disi,disambiguationi));
        }
        return CommonResponse.createForSuccess(disambiguationi);
    }

//    @Override
//    public CommonResponse<List<Disambiguation>> selectTableByRI(String ri) {
//        List<Integer> ids = disambiguationMapper.selectIdByRI(ri);
//        if (ids.isEmpty()) {
//            return CommonResponse.createForError("没有");
//        }
//        List<Disambiguation> disambiguation = new ArrayList<>();
//        for (int i = 0; i < ids.size(); i++) {
//            List<Dis> dis = disambiguationMapper.selectListAu(ids.get(i));
//            disambiguation.addAll(datacope(dis,disambiguation));
//        }
//        return CommonResponse.createForSuccess(disambiguation);
//    }

//    @Override
//    public CommonResponse<List<Disambiguation>> selectTableByOI(String oi) {
//        List<Integer> ids = disambiguationMapper.selectIdByOI(oi);
//        if (ids.isEmpty()) {
//            return CommonResponse.createForError("没有");
//        }
//        List<Disambiguation> disambiguation = new ArrayList<>();
//        for (int i = 0; i < ids.size(); i++) {
//            List<Dis> dis = disambiguationMapper.selectListAu(ids.get(i));
//            disambiguation.addAll(datacope(dis,disambiguation));
//        }
//        return CommonResponse.createForSuccess(disambiguation);
//    }

}
