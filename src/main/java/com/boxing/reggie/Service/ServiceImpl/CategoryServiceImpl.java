package com.boxing.reggie.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boxing.reggie.Entity.Category;

import com.boxing.reggie.Entity.Dish;
import com.boxing.reggie.Entity.Setmeal;
import com.boxing.reggie.Service.CategoryService;
import com.boxing.reggie.Service.DishService;
import com.boxing.reggie.Service.SetmealService;
import com.boxing.reggie.common.CustomException;
import com.boxing.reggie.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/29 21:29
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
     @Autowired
     private DishService dishService;

     @Autowired
     private SetmealService setmealService;


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrappers=new LambdaQueryWrapper();

        lambdaQueryWrappers.eq(Dish::getCategoryId,id);

        int count = dishService.count(lambdaQueryWrappers);

        if (count>0) {
            throw new CustomException("已关联菜品,不能删除");
        }
        //查询当前分类是否关联了套餐，如果已经关联则，抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper =new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        int count1 = setmealService.count();
        if (count1>0) {
            throw new CustomException("已关联菜品,不能删除");
        }

        super.removeById(id);
    }
}
