package com.boxing.reggie.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boxing.reggie.Entity.Category;
import com.boxing.reggie.Entity.Employee;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/29 21:28
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
