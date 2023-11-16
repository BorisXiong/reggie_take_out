package com.boxing.reggie.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boxing.reggie.Entity.Dish;
import com.boxing.reggie.Entity.DishDto;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/12/4 22:49
 */
public interface DishService extends IService<Dish> {

    public void  saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息
    public DishDto getByDishIdWithFlavor(Long id);

    public void  updateWithFlavor(DishDto DishDto);


}
