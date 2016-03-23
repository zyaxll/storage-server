package com.b5m.storage.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠码
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: chonglou
 * @version: 1.0
 * @createdate: 2015/12/11
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015/12/11  chonglou          1.0             Why & What is modified
 */
@Table(name="t_core_activation_code")
public class ActivationCode implements Serializable {

    private static final long serialVersionUID = -8705230165881170196L;

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull(message = "{ActivationCode.code.null}")
    @Length(min = 5, max = 20, message = "{ActivationCode.code.length.illegal}")
    @Pattern(regexp = "[a-zA-Z0-9]{5,20}", message = "{ActivationCode.code.illegal}")
    //激活码
    @Column(name = "code")
    private String code;

    //赠品id
    @Column(name = "gift_id")
    private String giftId;

    //过期时间
    @Column(name = "dead_line")
    private Date deadLine;

    //批次名
    @Column(name = "group_name")
    private String groupName;

    //批次名下的批次号
    @Column(name = "group_code")
    private String groupCode;

    @JSONField(serialize = false)
    //创建时间
    @Column(name = "create_time")
    private Date createTime;

    @JSONField(serialize = false)
    //更新时间
    @Column(name = "update_time")
    private Date updateTime;

    @JSONField(serialize = false)
    //创建用户
    @Column(name = "create_user")
    private String createUser;

    @JSONField(serialize = false)
    //更新用户
    @Column(name = "update_user")
    private String updateUser;

    //状态  0: 无效  1: 有效
    @Deprecated
    @Column(name = "status")
    private Integer status;

    @Deprecated
    //使用状态   1: 可使用, 0: 不可使用
    @Column(name = "available_status")
    private Integer availableStatus;

    public Integer getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Integer availableStatus) {
        this.availableStatus = availableStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
