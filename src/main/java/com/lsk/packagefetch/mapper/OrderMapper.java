package com.lsk.packagefetch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsk.packagefetch.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
