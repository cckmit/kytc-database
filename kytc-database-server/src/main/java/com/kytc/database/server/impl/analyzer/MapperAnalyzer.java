package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.helper.AnalyzerHelper;
import com.kytc.database.server.service.ayalyzer.Analyzer;
import com.kytc.database.server.utils.DatabaseUtils;

import lombok.extern.slf4j.Slf4j;

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
public class MapperAnalyzer implements Analyzer{
    @Autowired
    public MapperAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    //MapperExAnalyzer
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,String description) {
        String line = "";
        for(ColumnResponse columnResponse:columnResponses){
            String name = DatabaseUtils.getJavaName(columnResponse.getColumnName());
            if(Arrays.asList("id","createdAt","createdBy","updatedAt","updatedBy","lastUpdatedAt").contains(name)){
                continue;
            }
            line+= DatabaseUtils.getJavaType(columnResponse.getDataType()) +" " +DatabaseUtils.getJavaName(columnResponse.getColumnName())+", ";
        }
        List<String> list = new ArrayList<>();
        list.add("package "+pkg+".dao.mapper;\n");
        list.add("import "+pkg+".dao.data."+DatabaseUtils.getDataClass(tableName)+";\n");
        list.add("public interface "+DatabaseUtils.getMapperClass(tableName)+" {\n");
        list.add("\tint deleteByPrimaryKey(Long id);");
        list.add("\n\tint insert("+DatabaseUtils.getDataClass(tableName)+" record);");
        list.add("\n\tint insertSelective("+DatabaseUtils.getDataClass(tableName)+" record);");
        list.add("\n\t"+DatabaseUtils.getDataClass(tableName)+" selectByPrimaryKey(Long id);");
        list.add("\n\tint updateByPrimaryKeySelective("+DatabaseUtils.getDataClass(tableName)+" record);");
        list.add("\n\tint updateByPrimaryKey("+DatabaseUtils.getDataClass(tableName)+" record);");
        list.add("}");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getMapperClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "dao"+ File.separator+"mapper";
    }
}
