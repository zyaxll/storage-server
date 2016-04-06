package com.b5m.storage.service.impl;

import com.b5m.core.service.impl.CommonService;
import com.b5m.storage.dao.PublicCodeMapper;
import com.b5m.storage.model.entity.PublicCode;
import com.b5m.storage.service.IPublicCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-28
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-28       Leo.li          1.0             TODO
 */
@Service
public class PublicCodeService extends CommonService<PublicCode, Long> implements IPublicCodeService {

    @Autowired(required = false)
    private PublicCodeMapper publicCodeMapper;

    @Autowired(required = false)
    public void setMapper(PublicCodeMapper mapper) {
        super.setMapper(mapper);
    }

}
