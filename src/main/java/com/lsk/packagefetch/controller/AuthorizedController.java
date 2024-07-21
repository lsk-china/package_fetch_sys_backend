package com.lsk.packagefetch.controller;

import com.lsk.packagefetch.aspect.annotation.RequireCaptcha;
import com.lsk.packagefetch.service.OrderService;
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

    @GetMapping("/my_order_statistics")
    public String myOrderStatistics() {
        return ResponseUtil
                .ok(orderService.myOrderStatistics())
                .build();
    }

    @RequireCaptcha
    @PostMapping("/create_order")
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
            @RequestParam(value = "comments", required = false) String comments
    ) {
        return ResponseUtil.ok().build();
    }
}
