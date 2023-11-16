package com.boxing.reggie.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boxing.reggie.Entity.*;

import java.util.List;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/12/4 22:49
 */
public interface SetmealService extends IService<Setmeal> {


    //新增套餐同时保存关系
    public  void  saveWithDish(SetmealDto setmealDto);

    void DeleteCatare(List<Long> ids);
}
