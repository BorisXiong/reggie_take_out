package com.boxing.reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxing.reggie.Entity.Category;
import com.boxing.reggie.Entity.Dish;
import com.boxing.reggie.Entity.DishDto;

import com.boxing.reggie.Service.CategoryService;
import com.boxing.reggie.Service.DishFlavorService;
import com.boxing.reggie.Service.DishService;
import com.boxing.reggie.common.R;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/12/12 21:38
 */

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {


    @Autowired
     private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public  R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }


    @GetMapping("/a")
    public R<String> lo(){

        return R.success("1111111111");
    }


    @GetMapping("/page")
    public R<Page> page (int page,int pageSize ,String name){
        //构造分页构造器

        Page<Dish> pageInfo=new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage=new Page<>();
        //对象拷贝


        //构造条件构造器
        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper();

        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName,name);

        //
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行查询

        dishService.page(pageInfo, lambdaQueryWrapper);


        //对象拷贝
//       IgnorePropertie忽略掉的属性
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        // 因为dish对象里面没有categoryname这个属性而dishdto里面有且她继承了dish类

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();//分类id
            Category category = categoryService.getById(categoryId);
            if (category!=null) {
                String categoryName= category.getName();

                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }



    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        DishDto dishDto = dishService.getByDishIdWithFlavor(id);

        return R.success(dishDto);
    }

    @PutMapping
    public  R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    @GetMapping("/list")
   public R<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        //构造查询条件
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        //更具sort排序，然后更根据更新时间排序
        queryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        return R.success(list);
   }


}
