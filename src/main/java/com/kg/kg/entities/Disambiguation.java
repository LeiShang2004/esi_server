package com.kg.kg.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Disambiguation {
    // Disambiguation entity
    @TableField(value = "author_id")
    private Integer id;
    @TableField(value = "all_names")
    private String[] name;
    @TableField(value = "affiliated_institution_names")
    private String[] institutions;
    @TableField(value = "affiliated_institution_ids")
    private String[] affiliatedInstitutionIds;
    @TableField(value = "research_direction")
    private String[] sc;
    @TableField(value = "collaboration_papers")
    private String[] paperId;
    @TableField(value = "paper_titles")
    private String[] paper;
    @TableField(value = "collaborator_names")
    private String[] coauthor;
    @TableField(value = "collaborator_ids")
    private String[] collaborator_author_ids;
    @TableField(value = "RI")
    private String RI;
    @TableField(value = "OI")
    private String OI;
}
