package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.mapper.OrderMapper;
import com.itheima.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
}
