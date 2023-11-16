package com.boxing.reggie.Entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/12/14 22:02
 */

@Data
public class DishDto extends Dish {

    List<DishFlavor> flavors = new ArrayList<>();

    private  String categoryName;

    private  Integer copies;
}
