/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.database.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 何志同
 * @Date: 2020/10/10 18:36
 * @Description:
 **/
@Data
public class ColumnIndexDTO implements Serializable {
    private String table;
    private boolean non_unique;//false代表唯一索引
    private String key_name;
    private String column_name;//字段名称

}