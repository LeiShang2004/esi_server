package com.kg.kg.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Dis {
    // Disambiguation entity
    @TableField(value = "authorId")
    private Integer authorId;
    @TableField(value = "allNames")
    private String allNames;
    @TableField(value = "affiliatedInstitutionNames")
    private String affiliatedInstitutionNames;
    @TableField(value = "affiliatedInstitutionIds")
    private String affiliatedInstitutionIds;
    @TableField(value = "research_direction")
    private String researchDirection;
    @TableField(value = "collaboration_papers")
    private String collaborationPapers;
    @TableField(value = "paper_titles")
    private String paperTitles;
    @TableField(value = "collaborator_names")
    private String collaboratorNames;
    @TableField(value = "collaborator_ids")
    private String collaboratorAuthorIds;
    @TableField(value = "RI")
    private String RI;
    @TableField(value = "OI")
    private String OI;


}
