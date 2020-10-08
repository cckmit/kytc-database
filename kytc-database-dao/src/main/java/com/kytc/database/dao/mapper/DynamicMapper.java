package com.kytc.database.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DynamicMapper {
    List<Map<String, Object>> selectSql(@Param("sql") String sql);

    Integer insertSql(@Param("sql") String sql);

    Integer updateSql(@Param("sql") String sql);

    Integer deleteSql(@Param("sql") String sql);
}