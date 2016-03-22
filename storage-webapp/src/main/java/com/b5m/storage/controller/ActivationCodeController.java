package com.b5m.storage.controller;

import com.b5m.storage.model.entity.ActivationCode;
import com.b5m.storage.service.IActivationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/{code}")
    @ResponseBody
    public ActivationCode getCode(@PathVariable String code) {
        return activationCodeService.selectByCode(code);
    }

}
