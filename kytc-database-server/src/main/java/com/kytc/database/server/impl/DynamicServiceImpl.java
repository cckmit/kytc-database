/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.database.server.impl;

import com.kytc.database.dao.data.DatabaseConfigData;
import com.kytc.database.dao.mapper.DatabaseConfigMapperEx;
import com.kytc.database.dao.mapper.DynamicMapper;
import com.kytc.database.server.service.DynamicService;
import com.kytc.framework.exception.BaseErrorCodeEnum;
import com.kytc.framework.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: 何志同
 * @Date: 2020/9/29 18:59
 * @Description:
 **/
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class DynamicServiceImpl implements DynamicService {
    private final ConcurrentMap<String,SqlSession> currentMap = new ConcurrentHashMap<>();
    private final DatabaseConfigMapperEx databaseConfigMapperEx;
    @Override
    public List<Map<String, Object>> select(String database, String sql) {
        if( !currentMap.containsKey(database) ){
            this.initDatabase(database);
        }
        SqlSession sqlSession = currentMap.get(database);
        try {
            DynamicMapper dynamicMapper = sqlSession.getMapper(DynamicMapper.class);
            return dynamicMapper.selectSql(sql);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            throw new BaseException(BaseErrorCodeEnum.SYSTEM_ERROR,e.getMessage(),e);
        }
    }

    @Override
    public void init() {
        List<DatabaseConfigData> list = this.databaseConfigMapperEx.listByCondition(null,null,null,null,
                null,null,0,-1);
        if(CollectionUtils.isEmpty(list)){
           return;
        }
        list.stream().forEach(databaseConfigData -> {
            this.initDatabase(databaseConfigData);
        });
    }

    private void initDatabase(String databaseName){
        DatabaseConfigData databaseConfigData = this.databaseConfigMapperEx.getByDatabaseName(databaseName);
        if( null != databaseConfigData ){
            this.initDatabase(databaseConfigData);
        }
    }

    private void initDatabase(DatabaseConfigData databaseConfigData){
        if(currentMap.containsKey(databaseConfigData.getDatabaseName())){
            return;
        }
        synchronized (this){
            if( currentMap.containsKey(databaseConfigData.getDatabaseName()) ){
                return;
            }
            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setDriver("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl(databaseConfigData.getDatabaseUrl());
            dataSource.setUsername(databaseConfigData.getDatabaseUsername());
            dataSource.setPassword(databaseConfigData.getDatabasePassword());
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            try {
                Resource[] resources = resolver.getResources("classpath*:mapper/*.xml");
                SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
                // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
                sessionFactory.setDataSource(dataSource);
                //sessionFactory.setTypeAliasesPackage("com.**.model");    // 扫描Model
                sessionFactory.setMapperLocations(resources);    // 扫描映射文件
                SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
                currentMap.put(databaseConfigData.getDatabaseName(),sqlSessionFactory.openSession());
                log.info("init database,databaseName:{},url:{}",databaseConfigData.getDatabaseName(),databaseConfigData.getDatabaseUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}