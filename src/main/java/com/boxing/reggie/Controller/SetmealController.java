package com.boxing.reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxing.reggie.Entity.*;
import com.boxing.reggie.Service.CategoryService;
import com.boxing.reggie.Service.SetmealService;
import com.boxing.reggie.common.R;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Log4j2
public class SetmealController {

        @Autowired
        SetmealService setmealService;

        @Autowired
       CategoryService categoryService;


    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);

        Page<SetmealDishDto> pageDtoInfo=new Page<>();
        //构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        //根据name进行模糊查询
        queryWrapper.like(!StringUtils.isEmpty(name),Setmeal::getName,name);
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //进行分页查询
        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,pageDtoInfo,"records");

        List<Setmeal> records=pageInfo.getRecords();

        List<SetmealDishDto> list= records.stream().map((item)->{
            SetmealDishDto setmealDto=new SetmealDishDto();

            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            //根据id查分类对象
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        pageDtoInfo.setRecords(list);

        return R.success(pageDtoInfo);
    }
    //新增套餐
    @PostMapping
    public R<String> save (@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }
    //删除套餐
    @DeleteMapping()
    public R<String> DeleteCategory(@RequestParam List<Long> ids){
        log.info("删除套餐 ");
       LambdaQueryWrapper lambdaQueryWrapper=new LambdaQueryWrapper();
        setmealService.DeleteCatare(ids);

        return R.success("删除套餐成功");
    }

}
