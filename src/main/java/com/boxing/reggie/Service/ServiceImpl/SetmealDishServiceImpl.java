package com.boxing.reggie.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boxing.reggie.Entity.SetmealDish;
import com.boxing.reggie.Service.SetmealDishService;
import com.boxing.reggie.mapper.SetmealDishMapper;
import com.boxing.reggie.mapper.SetmealMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

}
