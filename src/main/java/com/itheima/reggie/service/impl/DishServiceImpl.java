package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    //新增菜品同时插入菜品对应的口味数据

    /**
     * 新增菜品，同时保存口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto){
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);
        Long dishId = dishDto.getId();
        //保存菜品口味数据到菜品口味表dish_flavor
//        dishFlavorService.saveBatch(dishDto.getFlavors());
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((items)->{
            items.setDishId(dishId);
            return items;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(dishDto.getFlavors());
    }
}
