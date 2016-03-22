package com.b5m.storage.dao;

import com.b5m.storage.model.entity.ActivationCode;

import java.util.List;

/**
 * @description: Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: chonglou
 * @version: 1.0
 * @createdate: 2015/12/11
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015/12/11  chonglou          1.0             Why & What is modified
 */
public interface ActivationCodeMapper {

    ActivationCode selectByCode(String code);

    List<String> listDistinctGroupName();

    List<String> listDistinctGroupCode(String groupName);
}
