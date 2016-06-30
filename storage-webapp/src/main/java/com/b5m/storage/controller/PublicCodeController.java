package com.b5m.storage.controller;

import com.b5m.core.entity.Condition;
import com.b5m.core.entity.PageImpl;
import com.b5m.storage.model.entity.PublicCode;
import com.b5m.storage.service.IPublicCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
@RequestMapping("/pc")
public class PublicCodeController {

    @Autowired
    private IPublicCodeService publicCodeService;

    @RequestMapping("/get")
    @ResponseBody
    public List<PublicCode> getCode(@RequestParam("id") Long id) {

//        publicCodeService.updateByAttribute(231L, new Query(new Query.Property("code", "abcdefghig"), new Query.Property("create_user", "123123")));
        List<Condition> conditions = Condition.like("code", "aaa")
                .add(Condition.eq("status", 1))
                .add(Condition.gt("id", 227)).getCnds();

//        PublicCode publicCode = publicCodeService.findOneByCondition(conditions);
//        List<PublicCode> list = new ArrayList<>();
//        list.add(publicCode);
//        PublicCode publicCode = new PublicCode();
//        publicCode.setStatus(1);
//        PageImpl<PublicCode> page = (PageImpl<PublicCode>) publicCodeService.findPage(publicCode, 5, 6);

        List<Long> list = new ArrayList<>();
        list.add(201L);
        list.add(202L);
        list.add(203L);
        list.add(204L);
        list.add(205L);
        list.add(206L);
        list.add(200L);

        return publicCodeService.findAll(list);
    }

}
