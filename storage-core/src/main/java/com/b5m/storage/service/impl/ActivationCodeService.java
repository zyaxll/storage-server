package com.b5m.storage.service.impl;

import com.b5m.storage.dao.ActivationCodeMapper;
import com.b5m.storage.model.entity.ActivationCode;
import com.b5m.storage.service.IActivationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class ActivationCodeService implements IActivationCodeService {

    @Autowired
    private ActivationCodeMapper activationCodeMapper;

    @Override
    public ActivationCode selectByCode(String code) {
        return activationCodeMapper.selectByCode(code);
    }

    @Override
    public ActivationCode get(Long id) {
        return null;
    }

    @Override
    public void save(ActivationCode activationCode) {

    }
}
