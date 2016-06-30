package com.b5m.storage.service.impl;

import com.b5m.core.service.impl.CommonService;
import com.b5m.storage.dao.ItemMapper;
import com.b5m.storage.entity.Item;
import com.b5m.storage.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-31
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-31       Leo.li          1.0             TODO
 */
@Service
public class ItemService extends CommonService<Item, Long> implements IItemService {

    @Autowired(required = false)
    private ItemMapper itemMapper;

    @Autowired(required = false)
    public void setMapper(ItemMapper mapper) {
        super.setMapper(mapper);
    }

}
