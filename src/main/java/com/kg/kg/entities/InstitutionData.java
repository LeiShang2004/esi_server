package com.kg.kg.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class InstitutionData {
    @TableField(value = "InstitutionName")
    protected String InstitutionName;
    @TableField(value = "AU")
    protected String AU;
    @TableField(value = "TI")
    protected String TI;
    @TableField(value = "SO")
    protected String SO;
    @TableField(value = "DE")
    protected String DE;
    @TableField(value = "LA")
    protected String LA;
    @TableField(value = "PD")
    protected String PD;
    @TableField(value = "PY")
    protected String PY;
    @TableField(value = "WC")
    protected String WC;
    @TableField(value = "AB")
    protected String AB;
    @TableField(value = "U1")
    protected String U1;
    @TableField(value = "U2")
    protected String U2;
    @TableField(value = "Z9")
    protected String Z9;
    @TableField(value = "SC")
    protected String SC;
}

