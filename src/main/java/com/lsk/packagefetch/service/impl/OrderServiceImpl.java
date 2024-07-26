package com.lsk.packagefetch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.packagefetch.helper.RedisHelper;
import com.lsk.packagefetch.mapper.OrderMapper;
import com.lsk.packagefetch.mapper.UserMapper;
import com.lsk.packagefetch.model.Order;
import com.lsk.packagefetch.model.User;
import com.lsk.packagefetch.service.OrderService;
import com.lsk.packagefetch.service.UserService;
import com.lsk.packagefetch.util.SecurityUtil;
import com.lsk.packagefetch.util.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${paging.items}")
    private Integer itemsPerPage;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RedisHelper redisHelper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;

    @Override
    @Transactional
    public void createOrder(Order order) {
        Integer uid = redisHelper.getUidByUsername(SecurityUtil.currentUsername());
        order.setCreateTime(new Date());
        order.setPublisher(uid);
        order.setIsDraft(false);
        orderMapper.insert(order);
        userMapper.increasePublishedOrderCount(uid);
        User user = redisHelper.queryCachedUser(uid);
        if (user == null)
            return;
        user.setPublishedOrderCount(user.getPublishedOrderCount() + 1);
        redisHelper.updateCachedUser(uid, user);
    }

    @Override
    public void draftOrder(Order order) {
        Integer uid = redisHelper.getUidByUsername(SecurityUtil.currentUsername());
        order.setPublisher(uid);
        order.setIsDraft(true);
    }

    @Override
    public Page<Order> myOrders(Integer pageNum) {
        Integer uid = redisHelper.getUidByUsername(SecurityUtil.currentUsername());
        return orderMapper.selectPage(
                new Page<Order>(pageNum, itemsPerPage),
                new QueryWrapper<Order>()
                        .eq("publisher", uid)
                        .eq("isDraft", false)
        );
    }

    @Override
    public Page<Order> myDrafts(Integer pageNum) {
        Integer uid = redisHelper.getUidByUsername(SecurityUtil.currentUsername());
        return orderMapper.selectPage(
                new Page<Order>(pageNum, itemsPerPage),
                new QueryWrapper<Order>()
                        .eq("publisher", uid)
                        .eq("isDraft", true)
        );
    }

    @Override
    public Page<Order> ordersToBeFetch(Integer pageNum) {
        return orderMapper.selectPage(
                new Page<Order>(pageNum, itemsPerPage),
                new QueryWrapper<Order>()
                        .eq("state", "PUBLISHED")
                        .eq("is_draft", false)
        );
    }

    @Override
    public Order queryOrderById(Integer id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new StatusCode(404, "Order Not Found");
        }
        return order;
    }

    @Override
    @Transactional
    public void finishOrder(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new StatusCode(404, "Order not found");
        }
        if (order.getIsDraft()) {
            throw new StatusCode(407, "Order is a draft");
        }
        if (!order.getState().equals("ACCEPTED")) {
            throw new StatusCode(407, "Invalid order state");
        }
        Integer uid = redisHelper.getUidByUsername(SecurityUtil.currentUsername());
        if (order.getPublisher().intValue() != uid.intValue()) {
            throw new StatusCode(403, "You are not the owner of this order");
        }
        order.setFetchTime(new Date());
        order.setState("FINISHED");
        orderMapper.updateById(order);
        Integer processorId = order.getProcessor();
        userMapper.increaseFinishedOrderCount(processorId);
        User user = redisHelper.queryCachedUser(processorId);
        if (user == null)
            return;
        user.setPublishedOrderCount(user.getPublishedOrderCount() + 1);
        redisHelper.updateCachedUser(processorId, user);
    }

    @Override
    @Transactional
    public void acceptOrder(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new StatusCode(404, "Order not found");
        }
        if (order.getState().equals("PUBLISHED")) {
            throw new StatusCode(407, "Invalid order state");
        }
        if (order.getIsDraft()) {
            throw new StatusCode(407, "Order is a draft");
        }
        Integer uid = redisHelper.getUidByUsername(SecurityUtil.currentUsername());
        if (order.getPublisher().intValue() == uid.intValue()) {
            throw new StatusCode(407, "Invalid operator");
        }
        order.setState("ACCEPTED");
        order.setProcessor(uid);
        order.setProcessTime(new Date());
        orderMapper.updateById(order);
    }

    @Override
    public void unDraftOrder(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new StatusCode(404, "Order not found");
        }
        if (!order.getIsDraft()) {
            throw new StatusCode(407, "Order is not a draft");
        }
        order.setIsDraft(false);
        orderMapper.updateById(order);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new StatusCode(404, "Order not found");
        }
        Integer uid = redisHelper.getUidByUsername(SecurityUtil.currentUsername());
        if (order.getPublisher().intValue() != uid.intValue()) {
            throw new StatusCode(403, "You are not the owner of this order");
        }
        orderMapper.deleteById(orderId);
    }

    @Override
    public void updateOrder(Order order) {
        Integer orderId = order.getId();
        Order oldOrder = orderMapper.selectById(orderId);
        if (oldOrder == null) {
            throw new StatusCode(404, "Order not found");
        }
        if (oldOrder.getIsDraft()) {
            throw new StatusCode(407, "Order is a draft");
        }
        Integer uid = redisHelper.getUidByUsername(SecurityUtil.currentUsername());
        if (order.getPublisher().intValue() != uid.intValue()) {
            throw new StatusCode(403, "You are not the owner of this order");
        }
        orderMapper.updateById(order);
    }

}
