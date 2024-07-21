package com.lsk.packagefetch.service.impl;

import com.lsk.packagefetch.helper.RedisHelper;
import com.lsk.packagefetch.mapper.OrderMapper;
import com.lsk.packagefetch.model.Order;
import com.lsk.packagefetch.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RedisHelper redisHelper;

    @Override
    public void createOrder(Order order) {

    }

    @Override
    public List<Order> myDrafts() {
        return Collections.emptyList();
    }

    @Override
    public void draftOrder(Order order) {

    }

    @Override
    public List<Order> myOrders() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Integer> myOrderStatistics() {
        return Collections.emptyMap();
    }

    @Override
    public void finishOrder(Integer orderId) {

    }

    @Override
    public void acceptOrder(Integer orderId) {

    }

    @Override
    public void unDraftOrder(Integer orderId) {

    }

    @Override
    public void deleteOrder(Integer orderId) {

    }

    @Override
    public void updateOrder(Order order) {

    }

    @Override
    public List<Order> ordersToBeFetch() {
        return Collections.emptyList();
    }
}
