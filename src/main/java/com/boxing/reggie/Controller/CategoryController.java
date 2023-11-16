package com.boxing.reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxing.reggie.Entity.Category;
import com.boxing.reggie.Service.CategoryService;
import com.boxing.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/29 21:32
 */

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String>  save(@RequestBody Category category){
        log.info("category",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page ,int pageSize){
         Page pageInfo=new Page(page,pageSize);

         //条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrap=new LambdaQueryWrapper<>();
        //添加排序条件
        lambdaQueryWrap.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, lambdaQueryWrap);

        return R.success(pageInfo);
    }
    @DeleteMapping
    public R<String> delete(long id){

        categoryService.removeById(id);

        return  R.success("删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("分类修改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper=new  LambdaQueryWrapper<>();

        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());

        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }
}
