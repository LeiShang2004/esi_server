package com.kg.kg.entities;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("paper") // 指定数据库表名
public class Paper {

    @TableField(value = "ID")
    private String ID;

    @TableField(value ="TI")
    private String TI;

    @TableField(value ="AB")
    private String AB;

    @TableField(value ="Z9")
    private String Z9;

    @TableField(value ="AU")
    private String AU;

    @TableField(value ="PY")
    private String PY;

    @TableField(value ="WC")
    private String WC;

    @TableField(value ="SO")
    private String SO;

    @TableField(value ="CR")
    private String CR;
}
