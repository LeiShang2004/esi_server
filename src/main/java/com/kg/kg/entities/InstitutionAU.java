package com.kg.kg.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


@Data
public class InstitutionAU {
    @TableField(value = "InstitutionName")
    protected String InstitutionName;
    @TableField(value = "AU")
    protected String AU;
    @TableField(value = "PaperNumber")
    protected int PaperNumber;
    @TableField(value = "Z9")
    protected String Z9;
    @TableField(value = "SC")
    protected String SC;
}
