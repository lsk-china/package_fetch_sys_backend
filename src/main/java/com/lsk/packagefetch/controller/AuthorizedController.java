package com.lsk.packagefetch.controller;

import com.lsk.packagefetch.aspect.annotation.RequireCaptcha;
import com.lsk.packagefetch.model.Order;
import com.lsk.packagefetch.service.OrderService;
import com.lsk.packagefetch.service.UserService;
import com.lsk.packagefetch.service.impl.OrderServiceImpl;
import com.lsk.packagefetch.util.DateUtil;
import com.lsk.packagefetch.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController("/api/authorized")
public class AuthorizedController {

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;


    @GetMapping("/my/statistics")
    public String myOrderStatistics() {
        return ResponseUtil
                .ok(userService.myOrderStatistics())
                .build();
    }

    @RequireCaptcha
    @PostMapping("/order/create")
    public String createOrder(
            @RequestParam("code_id") String codeID,
            @RequestParam("code_content") String codeContent,
            @RequestParam("fetch_address") String fetchAddress,
            @RequestParam("fetch_code") String fetchCode,
            @RequestParam("award") Integer award,
            @RequestParam("size_and_weight") String sizeAndWeight,
            @RequestParam("deliver_address") String deliverAddress,
            @RequestParam("deliver_time") String deliverTime,
            @RequestParam("contact_tel") String contactTel,
            @RequestParam("express_company") String expressCompany,
            @RequestParam("express_no") String expressNo,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "comments", required = false) String comments,
            @RequestParam(value = "draft", required = false, defaultValue = "false") String draft
    ) {
        Order order = new Order();
        order.setFetchAddress(fetchAddress);
        order.setFetchCode(fetchCode);
        order.setAward(award);
        order.setSizeAndWeight(sizeAndWeight);
        order.setDeliverAddress(deliverAddress);
        order.setDeliverTime(DateUtil.stringToDate(deliverTime));
        order.setContactTel(contactTel);
        order.setExpressCompany(expressCompany);
        order.setExpressNo(expressNo);
        order.setImage(image);
        order.setComments(comments);
        if ("true".equals(draft)) {
            orderService.draftOrder(order);
        } else {
            orderService.createOrder(order);
        }
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/query")
    public String queryOrderInfo(@RequestParam("order_id") Integer orderId) {
        return ResponseUtil.ok(
                orderService.queryOrderById(orderId)
        ).build();
    }

    @GetMapping("/my/orders")
    public String myOrders(
            @RequestParam("page") Integer page
    ) {
        return ResponseUtil.ok(
                orderService.myOrders(page)
        ).build();
    }

    @GetMapping("/my/drafts")
    public String myDrafts(
            @RequestParam("page") Integer page
    ) {
        return ResponseUtil.ok(
                orderService.myDrafts(page)
        ).build();
    }

    @GetMapping("/order/delete")
    public String deleteOrder(@RequestParam("order_id") Integer orderId) {
        orderService.deleteOrder(orderId);
        return ResponseUtil.ok().build();
    }

    @RequireCaptcha
    @GetMapping("/order/modify")
    public String modifyOrder(
            @RequestParam("order_id") Integer orderId,
            @RequestParam(value = "fetch_address", required = false) String fetchAddress,
            @RequestParam(value = "fetch_code", required = false) String fetchCode,
            @RequestParam(value = "award", required = false) Integer award,
            @RequestParam(value = "size_and_weight", required = false) String sizeAndWeight,
            @RequestParam(value = "deliver_address", required = false) String deliverAddress,
            @RequestParam(value = "deliver_time", required = false) String deliverTime,
            @RequestParam(value = "contact_tel", required = false) String contactTel,
            @RequestParam(value = "express_company", required = false) String expressCompany,
            @RequestParam(value = "express_no", required = false) String expressNo,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "comments", required = false) String comments

    ) {
        Order order = new Order();
        order.setId(orderId);
        order.setFetchAddress(fetchAddress);
        order.setFetchCode(fetchCode);
        order.setAward(award);
        order.setSizeAndWeight(sizeAndWeight);
        order.setDeliverAddress(deliverAddress);
        order.setDeliverTime(DateUtil.stringToDate(deliverTime));
        order.setContactTel(contactTel);
        order.setExpressCompany(expressCompany);
        order.setExpressNo(expressNo);
        order.setImage(image);
        order.setComments(comments);
        orderService.updateOrder(order);
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/accept")
    public String acceptOrder(@RequestParam("order_id") Integer orderId) {
        orderService.acceptOrder(orderId);
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/finish")
    public String finishOrder(@RequestParam("order_id") Integer orderId) {
        orderService.finishOrder(orderId);
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/unaccepted")
    public String unacceptedOrders(@RequestParam("page") Integer page) {
        return ResponseUtil.ok(
                orderService.ordersToBeFetch(page)
        ).build();
    }

}
