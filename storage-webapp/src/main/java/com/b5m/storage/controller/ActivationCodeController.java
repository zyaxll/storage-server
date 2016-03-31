package com.b5m.storage.controller;

import com.b5m.storage.model.entity.ActivationCode;
import com.b5m.storage.service.IActivationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-22
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-22       Leo.li          1.0             TODO
 */
@Controller
@RequestMapping("/ac")
public class ActivationCodeController {

    @Autowired
    private IActivationCodeService activationCodeService;

    @RequestMapping("/get")
    @ResponseBody
    public ActivationCode getCode(@Valid ActivationCode code) {
        if (code.getId() == null) {
            throw new RuntimeException("123");
        }
        return activationCodeService.selectByCode(code.getCode());
    }

}
