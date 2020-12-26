package com.kytc.database.server.impl.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.dto.AnalyzerDTO;
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
    public List<String> analyzer(AnalyzerDTO analyzerDTO) {
        List<ColumnResponse> columnResponses = analyzerDTO.getColumnResponses();
        String pkg = analyzerDTO.getPkg();
        String tableName = analyzerDTO.getTableName();
        Map<Boolean, Map<String, java.util.List<ColumnIndexDTO>>> columnMap = analyzerDTO.getColumnMap();
        List<String> list = new ArrayList<>();
        list.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperExClass(tableName)+"\">\n" +
                "\t<sql id=\"queryCondition\">\n" +
                "\t\t<where>");
        ColumnResponse priColumn = DatabaseUtils.getPriColumn(columnResponses);
        for(ColumnResponse columnResponse:columnResponses) {
            String name = columnResponse.getJavaName();
            if (Arrays.asList("createdAt", "createdBy", "updatedAt", "updatedBy", "lastUpdatedAt","isDeleted").contains(name)) {
                continue;
            }
            if(columnResponse.getColumnType().toLowerCase().contains("text")){
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
        if(!CollectionUtils.isEmpty(columnMap) && columnMap.containsKey(false)){
            Map<String,List<ColumnIndexDTO>> map = columnMap.get(false);
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
                    line1+=" "+columnResponse.getJavaType()+" "+columnResponse.getJavaName()+",";
                    column += DatabaseUtils.getDTOName(columnResponse.getColumnName())+"And";
                    whereStr += "\n\t\tAND "+columnResponse.getColumnName()+" = #{"+columnResponse.getJavaName()+",jdbcType="+DatabaseUtils.getMapperDataType(columnResponse.getDataType())+"} ";
                }
                line1 = line1.substring(0,line1.length()-1);
                column = column.substring(0,column.length()-3);
                whereStr = whereStr.substring(whereStr.indexOf("AND")+3);
                if(DatabaseUtils.isRealDelete(columnResponses)){
                    list.add("\t<delete id=\"deleteBy"+column+"\">");
                    list.add("\t\tdelete from "+tableName);
                    list.add("\t\twhere "+whereStr);
                    list.add("\t</delete>");
                }else{
                    list.add("\t<update id=\"deleteBy"+column+"\">");
                    list.add("\t\tupdate "+tableName+"\n\t\tset is_deleted = 0");
                    list.add("\t\twhere "+whereStr);
                    list.add("\t</update>");
                }
                list.add("\t<select id=\"getBy"+column+"\" resultMap=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperClass(tableName)+".BaseResultMap\">");
                list.add("\t\tselect");
                list.add("\t\t\t<include refid=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperClass(tableName)+".Base_Column_List\"></include>");
                list.add("\t\tfrom "+tableName+"");
                list.add("\t\twhere "+whereStr);
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
