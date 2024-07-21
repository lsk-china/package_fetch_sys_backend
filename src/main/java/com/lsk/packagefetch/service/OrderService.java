package com.lsk.packagefetch.service;

import com.lsk.packagefetch.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void createOrder(Order order);
    void draftOrder(Order order);

    List<Order> myOrders();
    List<Order> myDrafts();
    List<Order> ordersToBeFetch();

    void deleteOrder(Integer orderId);

    void updateOrder(Order order);
    void unDraftOrder(Integer orderId);

    void acceptOrder(Integer orderId);
    void finishOrder(Integer orderId);

    Map<String, Integer> myOrderStatistics();
}
