package com.lsk.packagefetch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.packagefetch.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void createOrder(Order order);
    void draftOrder(Order order);

    Page<Order> myOrders(Integer pageNum);
    Page<Order> myDrafts(Integer pageNum);
    Page<Order> ordersToBeFetch(Integer pageNum);

    Order queryOrderById(Integer id);

    void deleteOrder(Integer orderId);

    void updateOrder(Order order);
    void unDraftOrder(Integer orderId);

    void acceptOrder(Integer orderId);
    void finishOrder(Integer orderId);
}
