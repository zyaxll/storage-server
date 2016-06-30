package com.b5m.storage.service;

import com.b5m.storage.model.entity.ActivationCode;

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
public interface IActivationCodeService extends IActivationCodeRpcService {

    ActivationCode selectByCode(String code);

}
