/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.database.server.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 何志同
 * @Date: 2020/9/29 18:52
 * @Description:
 **/
public interface DynamicService {
    List<Map<String,Object>> select(String database, String sql);

    void init();
}