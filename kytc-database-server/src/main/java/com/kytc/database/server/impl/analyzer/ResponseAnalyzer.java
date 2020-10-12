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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class ResponseAnalyzer implements Analyzer{
    @Autowired
    public ResponseAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,
                                 Map<Boolean, Map<String,List<ColumnIndexDTO>>> columnMap, String description) {
        List<String> list = new ArrayList<>();
        list.add("package "+pkg+".response;\n");
        list.add("import lombok.Data;");
        list.add("import io.swagger.annotations.ApiModel;");
        list.add("import io.swagger.annotations.ApiModelProperty;");
        list.add("\nimport java.io.Serializable;");
        list.add("import java.util.Date;");
        list.add("\n@Data");
        list.add("@ApiModel(\""+description+" response\")");
        list.add("public class "+ DatabaseUtils.getDTOName(tableName)+ NameContant.DATABASE_RESPONSE+" implements Serializable {");
        list.add("\tprivate static final long serialVersionUID = 1L;");
        for(ColumnResponse columnResponse:columnResponses){
            list.add("\t@ApiModelProperty(\""+columnResponse.getColumnComment()+"\")");
            list.add("\tprivate "+ DatabaseUtils.getJavaType(columnResponse.getColumnType())+" "+ DatabaseUtils.getJavaName(columnResponse.getColumnName())+";");
        }
        list.add("}");
        return list;
    }


    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getResponseClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "response";
    }
}
