package com.boxing.reggie.Entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/29 21:21
 */

@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //类型 1 菜品分类 2 套餐分类
    private Integer type;

    //分类名称
    private String name;

    //顺序
    private Integer sort;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    //修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    @TableField(select = false)
    private Integer isDeleted;



}
