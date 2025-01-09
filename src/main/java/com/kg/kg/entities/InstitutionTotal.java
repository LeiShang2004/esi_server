package com.kg.kg.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class InstitutionTotal {
    @TableField(value = "InstitutionName")
    protected String InstitutionName;
    @TableField(value = "SC")
    protected String SC;
    @TableField(value = "Y2014")
    protected String Y2014;
    @TableField(value = "Y2015")
    protected String Y2015;
    @TableField(value = "Y2016")
    protected String Y2016;
    @TableField(value = "Y2017")
    protected String Y2017;
    @TableField(value = "Y2018")
    protected String Y2018;
    @TableField(value = "Y2019")
    protected String Y2019;
    @TableField(value = "Y2020")
    protected String Y2020;
    @TableField(value = "Y2021")
    protected String Y2021;
    @TableField(value = "Y2022")
    protected String Y2022;
    @TableField(value = "Y2023")
    protected String Y2023;
    @TableField(value = "total")
    protected String total;
}
