package com.boxing.reggie.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boxing.reggie.Entity.*;
import com.boxing.reggie.Service.SetmealDishService;
import com.boxing.reggie.Service.SetmealService;
import com.boxing.reggie.common.CustomException;
import com.boxing.reggie.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/12/4 22:51
 */

@Service
public class SetmealImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {


    @Autowired
    private SetmealDishService SetmealDishService;


    public void saveWithDish(SetmealDto SetmealDto) {
        this.save(SetmealDto);

        List<SetmealDish> setmealDishes = SetmealDto.getSetmealDishes();

        //遍历
        setmealDishes.stream().map((item)->{
            item.setSetmealId(SetmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        SetmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void DeleteCatare(List<Long> ids) {

            //先判断一下能不能删，如果status为1，则套餐在售，不能删
            //select * from setmeal where id in (ids) and status = 1
            LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealLambdaQueryWrapper.in(Setmeal::getId, ids);
            setmealLambdaQueryWrapper.eq(Setmeal::getStatus, 1);
            int count = this.count(setmealLambdaQueryWrapper);


            if (count > 0) {
                throw new CustomException("套餐正在售卖中，请先停售再进行删除");
            }
            //如果没有在售套餐，则直接删除
            this.removeByIds(ids);
            //继续删除
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
            SetmealDishService.removeByIds(ids);



    }


}
