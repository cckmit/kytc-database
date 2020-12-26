package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
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
public class ServiceAnalyzer implements Analyzer{
    @Autowired
    public ServiceAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    @Override
    public List<String> analyzer(AnalyzerDTO analyzerDTO) {
        List<ColumnResponse> columnResponses = analyzerDTO.getColumnResponses();
        String pkg = analyzerDTO.getPkg();
        String tableName = analyzerDTO.getTableName();
        Map<Boolean, Map<String, java.util.List<ColumnIndexDTO>>> columnMap = analyzerDTO.getColumnMap();
        List<String> list = new ArrayList<>();
        ColumnResponse priColumn = DatabaseUtils.getPriColumn(columnResponses);
        list.add("package "+pkg+".server.service;\n");
        list.add("import "+pkg+".request."+DatabaseUtils.getRequestClass(tableName)+";");
        list.add("import "+pkg+".request."+DatabaseUtils.getSearchRequestClass(tableName)+";");
        list.add("import "+pkg+".response."+DatabaseUtils.getResponseClass(tableName)+";");
        list.add("import com.kytc.framework.web.common.BasePageResponse;\n");
        list.add("\npublic interface "+DatabaseUtils.getServiceClass(tableName)+" {");
        String line = "\n\tBasePageResponse<"+ DatabaseUtils.getResponseClass(tableName)+"> listByCondition(";
        line += "\n\t\t"+ DatabaseUtils.getSearchRequestClass(tableName)+" request );";
        list.add(line);
        list.add("\n\tLong add("+DatabaseUtils.getRequestClass(tableName)+" request);");
        list.add("\n\tboolean update("+DatabaseUtils.getRequestClass(tableName)+" request);");
        list.add("\n\t"+DatabaseUtils.getResponseClass(tableName)+" detail(Long id);");
        list.add("\n\tboolean delete(Long id);");
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
                for(ColumnIndexDTO columnIndexDTO:columnIndexDTOList){
                    ColumnResponse columnResponse = columnResponses.stream().filter(columnResponse1 -> columnResponse1.getColumnName().equalsIgnoreCase(columnIndexDTO.getColumn_name())).findFirst().get();
                    line1+=" "+columnResponse.getJavaType()+" "+columnResponse.getJavaName()+",";
                }
                line1 = line1.substring(0,line1.length()-1);
                list.add("\n\tboolean delete("+line1+" );");
            }
        }
        list.add("}");
        return list;
    }


    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getServiceClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "server"+ File.separator+"service";
    }
}
