package com.kytc.database.server.impl.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.dto.ColumnIndexDTO;
import com.kytc.database.server.helper.AnalyzerHelper;
import com.kytc.database.server.service.ayalyzer.Analyzer;
import com.kytc.database.server.utils.DatabaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * <a style="display:none">简单描述</a>.
 * <a style="display:none">详细描述</a><p></p>
 * <p><strong>目的:</strong></p>
 * <p><strong>原因:</strong></p>
 * <p><strong>用途:</strong></p>
 *
 * @author: 何志同
 * @date: 2019-08-24 22:39
 * @see <a target="_blank" href="">参考文档</a>
 **/
@Component
@Slf4j
public class MapperExImplAnalyzer implements Analyzer{
    @Autowired
    public MapperExImplAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,
                                 Map<Boolean, Map<String,List<ColumnIndexDTO>>> columnMap, String description) {
        String dtoName = DatabaseUtils.getDTOName(tableName);
        List<String> list = new ArrayList<>();
        list.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperExClass(tableName)+"\">\n" +
                "\t<sql id=\"queryCondition\">\n" +
                "\t\t<where>");
        ColumnResponse priColumn = DatabaseUtils.getPriColumn(columnResponses);
        for(ColumnResponse columnResponse:columnResponses) {
            String name = DatabaseUtils.getJavaName(columnResponse.getColumnName());
            if (Arrays.asList("createdAt", "createdBy", "updatedAt", "updatedBy", "lastUpdatedAt","isDeleted").contains(name)) {
                continue;
            }
            list.add("\t\t\t<if test=\"" + name + " != null \">");
            list.add("\t\t\t\tand " + columnResponse.getColumnName() + " = #{" + name + ",jdbcType=" + DatabaseUtils.getMapperDataType(columnResponse.getDataType()) + "}");
            list.add("\t\t\t</if>");
        }
        if(!DatabaseUtils.isRealDelete(columnResponses)){
            list.add("\t\t\tand is_deleted = 0");
        }
        list.add("\t\t</where>");
        list.add("\t</sql>");
        list.add("\t<select id=\"listByCondition\" resultMap=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperClass(tableName)+".BaseResultMap\">");
        list.add("\t\tselect ");
        list.add("\t\t<include refid=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperClass(tableName)+".Base_Column_List\"></include>");
        list.add("\t\tfrom "+tableName+"");
        list.add("\t\t<include refid=\"queryCondition\"></include>");
        list.add("\t\torder by updated_at desc");
        list.add("\t\t<if test=\"limit > 0\">\n\t\t\tlimit #{start},#{limit}\n\t\t</if>");
        list.add("\t</select>");
        list.add("\t<select id=\"countByCondition\" resultType=\"Long\">");
        list.add("\t\tselect");
        list.add("\t\t\tcount(1)");
        list.add("\t\tfrom "+tableName+"");
        list.add("\t\t<include refid=\"queryCondition\"></include>");
        list.add("\t</select>");
        if(!CollectionUtils.isEmpty(columnMap) && columnMap.containsKey(true)){
            Map<String,List<ColumnIndexDTO>> map = columnMap.get(true);
            for(String key:map.keySet()){
                List<ColumnIndexDTO> columnIndexDTOList = map.get(key);
                if(columnIndexDTOList.size()==1){
                    if( columnIndexDTOList.get(0).getColumn_name().equalsIgnoreCase(priColumn.getColumnName()) ){
                        continue;
                    }
                }
                String line1 = "";
                String column = "";
                String whereStr = "";
                for(ColumnIndexDTO columnIndexDTO:columnIndexDTOList){
                    ColumnResponse columnResponse = columnResponses.stream().filter(columnResponse1 -> columnResponse1.getColumnName().equalsIgnoreCase(columnIndexDTO.getColumn_name())).findFirst().get();
                    line1+=" "+DatabaseUtils.getJavaType(columnResponse.getDataType())+" "+DatabaseUtils.getJavaName(columnResponse.getColumnName())+",";
                    column += DatabaseUtils.getDataClass(columnResponse.getColumnName())+"And";
                    whereStr += "\nAND"+columnResponse.getColumnName()+" = #{"+DatabaseUtils.getJavaName(columnResponse.getColumnName())+",jdbcType="+DatabaseUtils.getMapperDataType(columnResponse.getDataType())+"} ";
                }
                line1 = line1.substring(0,line1.length()-1);
                column = column.substring(0,column.length()-3);
                whereStr = whereStr.substring(whereStr.indexOf("AND")+3);
                list.add("\t<delete id=\"deleteBy"+column+"\">");
                if(DatabaseUtils.isRealDelete(columnResponses)){
                    list.add("\t\tdelete from "+tableName);
                }else{
                    list.add("\t\tupdate "+tableName+"\n\t\tset is_deleted = 0");
                }
                list.add("\t\twhere"+whereStr);
                list.add("\t<delete>");
                list.add("\t<select id=\"getBy"+column+"\" resultMap=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperClass(tableName)+".BaseResultMap\">");
                list.add("\t\tselect");
                list.add("\t\t\t<include refid=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperClass(tableName)+".Base_Column_List\"></include>");
                list.add("\t\tfrom "+tableName+"");
                list.add("\t\twhere"+whereStr);
                list.add("\t\tlimit 1");
                list.add("\t</select>");
            }
        }
        list.add("</mapper>");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getMapperExClass(tableName)+".xml";
    }
    @Override
    public String getPackage() {
        return "xml";
    }
}
