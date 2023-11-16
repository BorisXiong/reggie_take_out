package com.boxing.reggie.Entity;

import lombok.Data;

import java.util.List;

@Data
public class SetmealDishDto extends SetmealDish {

    private List<SetmealDish> setmealDishes;

    private String CategoryName;

}
