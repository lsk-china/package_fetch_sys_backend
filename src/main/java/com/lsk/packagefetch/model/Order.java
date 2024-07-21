package com.lsk.packagefetch.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("order")
public class Order {

    @TableId(type= IdType.AUTO)
    @TableField("id")
    private Integer id;

    @TableField("publisher")
    private Integer publisher;

    @TableField("create_time")
    private Date createTime;

    @TableField("is_draft")
    private Boolean isDraft;

    @TableField("fetch_address")
    private String fetchAddress;

    @TableField("fetch_code")
    private String fetchCode;

    @TableField("award")
    private Integer award;

    @TableField("size_and_weight")
    private String sizeAndWeight;

    @TableField("deliver_address")
    private String deliverAddress;

    @TableField("deliver_time")
    private Date deliverTime;

    @TableField("contact_tel")
    private String contactTel;

    @TableField("express_company")
    private String expressCompany;

    @TableField("express_no")
    private String expressNo;

    @TableField("image")
    private String image;

    @TableField("processor")
    private Integer processor;

    @TableField("process_time")
    private Date processTime;

    @TableField("fetch_time")
    private Date fetchTime;

    @TableField("comments")
    private String comments;

}
