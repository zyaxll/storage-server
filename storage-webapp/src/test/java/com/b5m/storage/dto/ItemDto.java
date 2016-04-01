package com.b5m.storage.dto;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-4-1
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-4-1       Leo.li          1.0             TODO
 */
public class ItemDto {

    @NotNull(message = "is not null")
    private Long id;

    @NotNull
    @Digits(integer = 10, fraction=2)
    private Double consumeAmount;

    @Pattern(regexp = "[a-z]{3,}")
    private String currency;

    @Null
    private Date effectTime;

    @Min(value = 2)
    @Max(value = 10)
    @DecimalMin(value = "1")
    @DecimalMax(value = "3")
    private Integer itemSubGroup;

    @AssertTrue
    private Boolean isExist;

    @AssertFalse
    private Boolean isFalse;

    @Size(min = 1, max = 5)
    private List<?> list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(Double consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Integer getItemSubGroup() {
        return itemSubGroup;
    }

    public void setItemSubGroup(Integer itemSubGroup) {
        this.itemSubGroup = itemSubGroup;
    }

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }

    public Boolean getFalse() {
        return isFalse;
    }

    public void setFalse(Boolean aFalse) {
        isFalse = aFalse;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
