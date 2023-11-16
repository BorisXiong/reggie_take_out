package com.boxing.reggie.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boxing.reggie.Entity.DishDto;
import com.boxing.reggie.Entity.DishFlavor;
import com.boxing.reggie.Service.DishFlavorService;
import com.boxing.reggie.mapper.DishFlavorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/12/12 21:35
 */

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {


}
