package com.b5m.storage.model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: ${TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: chonglou
 * @version: 1.0
 * @createdate: 16-2-4
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 16-2-4  chonglou          1.0             Why & What is modified
 */
@Table(name="t_core_public_code")
public class PublicCode implements Serializable {

    private static final long serialVersionUID = 5989771870965852871L;

    @Id
    @Column(name = "id")
    private Long id;

    //激活码
    @Column(name = "code")
    private String code;

    //赠品id
    @Column(name = "gift_ids")
    private String giftIds;

    @Column(name = "amount")
    private Integer amount;

    //过期时间
    @Column(name = "dead_line")
    private Date deadLine;

    //状态  0: 无效  1: 有效
    @Column(name = "status")
    private Integer status;

    //渠道
    @Column(name = "channel")
    private String channel;

    //创建时间
    @Column(name = "create_time")
    private Date createTime;

    //更新时间
    @Column(name = "update_time")
    private Date updateTime;

    //创建用户
    @Column(name = "create_user")
    private String createUser;

    //更新用户
    @Column(name = "update_user")
    private String updateUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGiftIds() {
        return giftIds;
    }

    public void setGiftIds(String giftIds) {
        this.giftIds = giftIds;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
