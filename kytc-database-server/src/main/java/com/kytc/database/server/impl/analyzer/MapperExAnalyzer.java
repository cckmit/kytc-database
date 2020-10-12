package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kytc.database.dao.dto.ColumnDTO;
import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.config.NameContant;
import com.kytc.database.server.dto.ColumnIndexDTO;
import com.kytc.database.server.helper.AnalyzerHelper;
import com.kytc.database.server.service.ayalyzer.Analyzer;
import com.kytc.database.server.utils.DatabaseUtils;
import lombok.RequiredArgsConstructor;
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
public class MapperExAnalyzer implements Analyzer{
    @Autowired
    public MapperExAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,
                                 Map<Boolean, Map<String,List<ColumnIndexDTO>>> columnMap, String description) {
        String line = "";
        for(ColumnResponse columnResponse:columnResponses){
            String name = DatabaseUtils.getJavaName(columnResponse.getColumnName());
            if(Arrays.asList("createdAt","createdBy","updatedAt","updatedBy","lastUpdatedAt").contains(name)){
                continue;
            }
            line+= DatabaseUtils.getJavaType(columnResponse.getDataType()) +" " +DatabaseUtils.getJavaName(columnResponse.getColumnName())+", ";
        }
        ColumnResponse priColumn = DatabaseUtils.getPriColumn(columnResponses);
        List<String> list = new ArrayList<>();
        list.add("package "+pkg+".dao.mapper;\n");
        list.add("\nimport "+pkg+".dao.data."+DatabaseUtils.getDataClass(tableName)+";\n");
        list.add("import java.util.List;");
        list.add("\npublic interface "+DatabaseUtils.getMapperExClass(tableName)+" extends "+DatabaseUtils.getMapperClass(tableName)+" {");
        list.add("\n\tList<"+DatabaseUtils.getDataClass(tableName)+"> listByCondition("+line+"int start, int limit);");
        list.add("\n\tLong countByCondition("+line.substring(0,line.length()-2)+");");
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
                for(ColumnIndexDTO columnIndexDTO:columnIndexDTOList){
                    ColumnResponse columnResponse = columnResponses.stream().filter(columnResponse1 -> columnResponse1.getColumnName().equalsIgnoreCase(columnIndexDTO.getColumn_name())).findFirst().get();
                    line1+=" "+DatabaseUtils.getJavaType(columnResponse.getDataType())+" "+DatabaseUtils.getJavaName(columnResponse.getColumnName())+",";
                    column += DatabaseUtils.getDTOName(columnResponse.getColumnName())+"And";
                }
                line1 = line1.substring(0,line1.length()-1);
                column = column.substring(0,column.length()-3);
                list.add("\n\tInteger deleteBy"+column+"("+line1+");");

                list.add("\n\t"+DatabaseUtils.getDataClass(tableName)+" getBy"+column+"("+line1+");");
            }
        }
        list.add("}");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getMapperExClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "dao"+ File.separator+"mapper";
    }
}
