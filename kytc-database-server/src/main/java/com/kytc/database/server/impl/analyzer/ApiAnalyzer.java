package com.kytc.database.server.impl.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.config.NameContant;
import com.kytc.database.server.dto.ColumnIndexDTO;
import com.kytc.database.server.helper.AnalyzerHelper;
import com.kytc.database.server.service.ayalyzer.Analyzer;
import com.kytc.database.server.utils.DatabaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * <a style="display:none">简单描述</a>.
 * <a style="display:none">详细描述</a><p></p>
 * <p><strong>目的:</strong></p>
 * <p><strong>原因:</strong></p>
 * <p><strong>用途:</strong></p>
 *
 * @author: 何志同
 * @date: 2019-08-24 21:57
 * @see <a target="_blank" href="">参考文档</a>
 **/
@Component
@Slf4j
public class ApiAnalyzer implements Analyzer{
    @Autowired
    public ApiAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    private String getUrl(String tableName){
        if(tableName.toLowerCase().startsWith("t_")){
            tableName = tableName.substring(2).toLowerCase();
        }
        return "/"+tableName.replaceAll("_","/");
    }
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,
                                 Map<Boolean, Map<String,List<ColumnIndexDTO>>> columnMap, String description) {
        List<String> list = new ArrayList<>();
        ColumnResponse priColumn = DatabaseUtils.getPriColumn(columnResponses);
        list.add("package "+pkg+".api;\n");
        list.add("import "+pkg+".request."+ DatabaseUtils.getRequestClass(tableName)+";");
        list.add("import "+pkg+".request."+ DatabaseUtils.getSearchRequestClass(tableName)+";");
        list.add("import "+pkg+".response."+ DatabaseUtils.getResponseClass(tableName)+";\n");
        list.add("import com.kytc.framework.web.common.BasePageResponse;");
        list.add("import org.springframework.web.bind.annotation.*;");
        list.add("import io.swagger.annotations.Api;\n" +
                "import io.swagger.annotations.ApiOperation;");
        list.add("\nimport javax.validation.Valid;");
        list.add("\n@Api(tags = \""+description+"操作\")");
        list.add("@RequestMapping(\""+getUrl(tableName)+"\")");
        list.add("public interface "+ DatabaseUtils.getDTOName(tableName)+NameContant.DATABASE_API+" {");
        list.add("\n\t@ApiOperation(\"查询"+description+"列表\")");
        list.add("\t@PostMapping(\"/infos\")");
        list.add("\tBasePageResponse<"+ DatabaseUtils.getResponseClass(tableName)+"> listByCondition(");
        list.add("\t\t@RequestBody "+ DatabaseUtils.getSearchRequestClass(tableName)+" request );");

        list.add("\n\t@ApiOperation(\"添加"+description+"数据\")");
        list.add("\t@PostMapping(\"/info\")");
        list.add("\tLong add(@RequestBody @Valid "+ DatabaseUtils.getRequestClass(tableName)+" request);");
        list.add("\n\t@ApiOperation(\"修改"+description+"数据\")");
        list.add("\t@PutMapping(\"/info\")");
        list.add("\tboolean update(@RequestBody @Valid "+ DatabaseUtils.getRequestClass(tableName)+" request);");
        list.add("\n\t@ApiOperation(\"删除"+description+"数据\")");
        list.add("\t@DeleteMapping(\"/{id}\")");
        list.add("\tboolean delete(@PathVariable(\"id\")Long id);");
        list.add("\n\t@ApiOperation(\"查询"+description+"详情\")");
        list.add("\t@GetMapping(\"/{id}\")");
        list.add("\t"+ DatabaseUtils.getResponseClass(tableName)+" detail(@PathVariable(\"id\")Long id);");
        if(!CollectionUtils.isEmpty(columnMap) && columnMap.containsKey(false)){
            Map<String,List<ColumnIndexDTO>> map = columnMap.get(false);
            for(String key:map.keySet()){
                List<ColumnIndexDTO> columnIndexDTOList = map.get(key);
                if(columnIndexDTOList.size()==1){
                    if( columnIndexDTOList.get(0).getColumn_name().equalsIgnoreCase(priColumn.getColumnName()) ){
                        continue;
                    }
                }
                list.add("\n\t@ApiOperation(\"根据唯一索引删除"+description+"数据\")");
                list.add("\t@DeleteMapping(\"/info\")");
                String line = "";
                for(ColumnIndexDTO columnIndexDTO:columnIndexDTOList){
                    ColumnResponse columnResponse = columnResponses.stream().filter(columnResponse1 -> columnResponse1.getColumnName().equalsIgnoreCase(columnIndexDTO.getColumn_name())).findFirst().get();
                    line+="@RequestParam(\""+DatabaseUtils.getJavaName(columnResponse.getColumnName())+"\") "+DatabaseUtils.getJavaType(columnResponse.getDataType())+" "+DatabaseUtils.getJavaName(columnResponse.getColumnName())+",";
                }
                line = line.substring(0,line.length()-1);
                list.add("\tboolean delete("+line+");");
            }
        }
        list.add("}");
        return list;
    }
    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getApiClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "api";
    }
}
