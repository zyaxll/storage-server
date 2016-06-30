package com.b5m.storage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 2016/03/31
 */
@Entity
@Table(name = Item.TABLE_TEST_ITEM)
@XmlRootElement(name = Item.CLASS_TEST_ITEM)
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

    public static final String TABLE_TEST_ITEM = "test_item";
    public static final String CLASS_TEST_ITEM = "TestItem";

    /**
     * 
     */
    @Id
    @GeneratedValue
    @Column(name = ID_COLUMN)
    private Long id;
    public static final String ID_PROPERTY = "id";
    public static final String ID_COLUMN = "id";

    /**
     * 
     */
    @Column(name = CONSUME_AMOUNT_COLUMN)
    private Double consumeAmount;
    public static final String CONSUME_AMOUNT_PROPERTY = "consumeAmount";
    public static final String CONSUME_AMOUNT_COLUMN = "consume_amount";

    /**
     * 
     */
    @Column(name = CURRENCY_COLUMN)
    private String currency;
    public static final String CURRENCY_PROPERTY = "currency";
    public static final String CURRENCY_COLUMN = "currency";

    /**
     * 
     */
    @Column(name = DISCOUNT_AMOUNT_COLUMN)
    private Double discountAmount;
    public static final String DISCOUNT_AMOUNT_PROPERTY = "discountAmount";
    public static final String DISCOUNT_AMOUNT_COLUMN = "discount_amount";

    /**
     * 
     */
    @Column(name = EFFECT_TIME_COLUMN)
    private Date effectTime;
    public static final String EFFECT_TIME_PROPERTY = "effectTime";
    public static final String EFFECT_TIME_COLUMN = "effect_time";

    /**
     * 
     */
    @Column(name = ITEM_GROUP_COLUMN)
    private Integer itemGroup;
    public static final String ITEM_GROUP_PROPERTY = "itemGroup";
    public static final String ITEM_GROUP_COLUMN = "item_group";

    /**
     * 
     */
    @Column(name = ITEM_NAME_COLUMN)
    private String itemName;
    public static final String ITEM_NAME_PROPERTY = "itemName";
    public static final String ITEM_NAME_COLUMN = "item_name";

    /**
     * 
     */
    @Column(name = ITEM_SUB_GROUP_COLUMN)
    private Integer itemSubGroup;
    public static final String ITEM_SUB_GROUP_PROPERTY = "itemSubGroup";
    public static final String ITEM_SUB_GROUP_COLUMN = "item_sub_group";

    /**
     * 
     */
    @Column(name = LEVEL_LIMIT_COLUMN)
    private Integer levelLimit;
    public static final String LEVEL_LIMIT_PROPERTY = "levelLimit";
    public static final String LEVEL_LIMIT_COLUMN = "level_limit";

    /**
     * 
     */
    @Column(name = PIID_COLUMN)
    private Long piid;
    public static final String PIID_PROPERTY = "piid";
    public static final String PIID_COLUMN = "piid";

    /**
     * 
     */
    @Column(name = PRICE_COLUMN)
    private Double price;
    public static final String PRICE_PROPERTY = "price";
    public static final String PRICE_COLUMN = "price";

    /**
     * 
     */
    @Column(name = STATUS_COLUMN)
    private String status;
    public static final String STATUS_PROPERTY = "status";
    public static final String STATUS_COLUMN = "status";

    /**
     * 
     */
    @Column(name = TIME_DURATION_COLUMN)
    private Long timeDuration;
    public static final String TIME_DURATION_PROPERTY = "timeDuration";
    public static final String TIME_DURATION_COLUMN = "time_duration";

    /**
     * 
     */
    @Column(name = TIMEOUT_COLUMN)
    private Date timeout;
    public static final String TIMEOUT_PROPERTY = "timeout";
    public static final String TIMEOUT_COLUMN = "timeout";

    /**
     * 
     */
    @Column(name = TYPE_COLUMN)
    private Integer type;
    public static final String TYPE_PROPERTY = "type";
    public static final String TYPE_COLUMN = "type";

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getConsumeAmount() {
        return this.consumeAmount;
    }

    public void setConsumeAmount(Double consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getDiscountAmount() {
        return this.discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Date getEffectTime() {
        return this.effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Integer getItemGroup() {
        return this.itemGroup;
    }

    public void setItemGroup(Integer itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemSubGroup() {
        return this.itemSubGroup;
    }

    public void setItemSubGroup(Integer itemSubGroup) {
        this.itemSubGroup = itemSubGroup;
    }

    public Integer getLevelLimit() {
        return this.levelLimit;
    }

    public void setLevelLimit(Integer levelLimit) {
        this.levelLimit = levelLimit;
    }

    public Long getPiid() {
        return this.piid;
    }

    public void setPiid(Long piid) {
        this.piid = piid;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimeDuration() {
        return this.timeDuration;
    }

    public void setTimeDuration(Long timeDuration) {
        this.timeDuration = timeDuration;
    }

    public Date getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Date timeout) {
        this.timeout = timeout;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
