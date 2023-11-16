package com.boxing.reggie.Service.ServiceImpl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boxing.reggie.Entity.Dish;
import com.boxing.reggie.Entity.DishDto;
import com.boxing.reggie.Entity.DishFlavor;
import com.boxing.reggie.Service.DishFlavorService;
import com.boxing.reggie.Service.DishService;
import com.boxing.reggie.mapper.DishMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/12/4 22:50
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {



    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        long dishId=dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();

        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
        }

        dishFlavorService.saveBatch(flavors);

    }



    @Override
    public DishDto getByDishIdWithFlavor(Long id) {

        // 只是关联查询两张表，没有涉及事务，不用加 @Transactional
        // 从dish表中查询 菜品的基本信息
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish,dishDto);

        //  从dish_flavor表查询 当前菜品对应的口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());

        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(list);

        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        // 更新dish表的基本信息
        this.updateById(dishDto);

        // 清理当前菜品对应口味数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        // 添加当前提交过来的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors.stream().map(item -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }




}
