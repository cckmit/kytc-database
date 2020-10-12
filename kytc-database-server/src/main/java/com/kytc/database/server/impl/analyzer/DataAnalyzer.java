package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kytc.database.server.dto.ColumnIndexDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.config.NameContant;
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
 * @date: 2019-08-24 21:57
 * @see <a target="_blank" href="">参考文档</a>
 **/
@Component
@Slf4j
public class DataAnalyzer implements Analyzer{
    @Autowired
    public DataAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,
                                 Map<Boolean, Map<String,List<ColumnIndexDTO>>> columnMap, String description) {
        List<String> list = new ArrayList<>();
        list.add("package "+pkg+".dao.data;\n");
        list.add("import lombok.Data;");
        list.add("\nimport java.io.Serializable;");
        list.add("import java.util.Date;");
        list.add("\n//"+description);
        list.add("@Data");
        list.add("public class "+ DatabaseUtils.getDataClass(tableName)+" implements Serializable {");
        list.add("\tprivate static final long serialVersionUID = 1L;");
        for(ColumnResponse columnResponse:columnResponses){
            list.add("\tprivate "+ DatabaseUtils.getJavaType(columnResponse.getColumnType())+" "+
                    DatabaseUtils.getJavaName(columnResponse.getColumnName())+";//"+columnResponse.getColumnComment());
        }
        list.add("}");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getDataClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "dao"+ File.separator+"data";
    }
}
