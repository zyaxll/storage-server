package com.b5m.storage.test.entity;

import com.b5m.storage.dto.ItemDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

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
public class TestValidator {

    private Validator validator;
    private ItemDto dto = new ItemDto();

    @Before
    public void before() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testValidator() {
        dto.setId(11L);
        dto.setConsumeAmount(12.22);

        Set<ConstraintViolation<ItemDto>> set = validator.validate(dto);
        for(ConstraintViolation<ItemDto> item : set){
            System.out.println(item.getPropertyPath() + " " + item.getMessage());
        }

    }
}
