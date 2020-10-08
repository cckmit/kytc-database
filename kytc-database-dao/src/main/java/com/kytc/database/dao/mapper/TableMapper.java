package com.kytc.database.dao.mapper;

import java.util.List;
import java.util.Map;

import com.kytc.database.dao.dto.TableDTO;
import com.kytc.database.dao.vo.TableVO;
import org.apache.ibatis.annotations.Param;

public interface TableMapper {
	/**
	 * @author fisher 
	 * @date 2018年5月13日下午5:00:03
	 * @description 
	 * @param database 数据库名称
	 * @return List<String>
	 */
	List<String> list(String database);
	/**
	 * @author fisher 
	 * @date 2018年5月13日下午5:33:28
	 * @description 获取表的详细信息
	 * @param database
	 * @param tableName
	 * @return TableDTO
	 */
	TableDTO detail(@Param("database") String database, @Param("tableName") String tableName);
	/**
	 * @author fisher 
	 * @date 2018年5月20日上午9:33:39
	 * @description 获取表的数据集
	 * @param vo
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> dataList(TableVO vo);
	/**
	 * @author fisher 
	 * @date 2018年5月20日上午9:34:13
	 * @description 获取表的数据总数
	 * @param vo
	 * @return Long
	 */
	Long dataCount(TableVO vo);
	/**
	 * @param database
	 * @param tableName
	 * @param priKey
	 * @param priValue
	 * @return Map
	 */
	Map<String,Object> dataDetail(@Param("database") String database, @Param("tableName") String tableName, @Param("priKey") String priKey, @Param("priValue") String priValue);
	/**
	 * @author fisher
	 * @description 添加表数据
	 * @date 2018年6月2日下午9:40:30
	 * @param map
	 * @return
	 */
	Boolean addData(@Param("map") Map<String, Object> map);
	/**
	 * @author fisher
	 * @description 修改表数据
	 * @date 2018年6月16日下午6:26:09
	 * @param map
	 * @return
	 */
	Boolean updateData(@Param("map") Map<String, Object> map);
	/**
	 * @author fisher
	 * @description 删除数据
	 * @date 2018年6月16日下午11:04:17
	 * @param database
	 * @param tableName
	 * @param priKey
	 * @param priValue
	 * @return
	 */
	Boolean deleteData(@Param("database") String database, @Param("tableName") String tableName, @Param("priKey") String priKey, @Param("priValue") String priValue);
}
