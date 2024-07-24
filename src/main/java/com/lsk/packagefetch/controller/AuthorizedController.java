package com.lsk.packagefetch.controller;

import com.lsk.packagefetch.aspect.annotation.RequireCaptcha;
import com.lsk.packagefetch.service.OrderService;
import com.lsk.packagefetch.service.UserService;
import com.lsk.packagefetch.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("/api/authorized")
public class AuthorizedController {

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @GetMapping("/my/statistics")
    public String myOrderStatistics() {
        return ResponseUtil
                .ok(orderService.myOrderStatistics())
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
            @RequestParam("deliver_time") String deliver_time,
            @RequestParam("contact_tel") String contact_tel,
            @RequestParam("express_company") String expressCompany,
            @RequestParam("express_no") String expressNo,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "comments", required = false) String comments,
            @RequestParam(value = "draft", required = false, defaultValue = "false") String draft
    ) {
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/query")
    public String queryOrderInfo(@RequestParam("order_id") Integer orderId) {
        return ResponseUtil.ok().build();
    }

    @GetMapping("/my/orders")
    public String myOrders(
            @RequestParam("page") Integer page
    ) {
        return ResponseUtil.ok().build();
    }

    @GetMapping("/my/drafts")
    public String myDrafts(
            @RequestParam("page") Integer page
    ) {
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/delete")
    public String deleteOrder(@RequestParam("order_id") Integer orderId) {
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/modify")
    public String modifyOrder(
            @RequestParam("order_id") Integer orderId,
            @RequestParam("fetch_address") String fetchAddress,
            @RequestParam("fetch_code") String fetchCode,
            @RequestParam("award") Integer award,
            @RequestParam("size_and_weight") String sizeAndWeight,
            @RequestParam("deliver_address") String deliverAddress,
            @RequestParam("deliver_time") String deliver_time,
            @RequestParam("contact_tel") String contact_tel,
            @RequestParam("express_company") String expressCompany,
            @RequestParam("express_no") String expressNo,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "comments", required = false) String comments

    ) {
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/accept")
    public String acceptOrder(@RequestParam("order_id") Integer orderId) {
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/finish")
    public String finishOrder(@RequestParam("order_id") Integer orderId) {
        return ResponseUtil.ok().build();
    }

    @GetMapping("/order/unaccepted")
    public String unacceptedOrders() {
        return ResponseUtil.ok().build();
    }

}
