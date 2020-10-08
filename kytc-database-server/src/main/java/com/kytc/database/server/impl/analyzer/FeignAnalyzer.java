package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
public class FeignAnalyzer implements Analyzer{
    @Autowired
    public FeignAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    private String getUrl(String tableName){
        if(tableName.toLowerCase().startsWith("t_")){
            tableName = tableName.substring(2).toLowerCase();
        }
        return "/"+tableName.replaceAll("_","/");
    }
    @Override
    public List<String> analyzer(String pkg,String tableName, List<ColumnResponse> columnResponses,String description) {
        List<String> list = new ArrayList<>();
        list.add("package "+pkg+".hub.server.remote;\n");
        list.add("import "+pkg+".api."+ DatabaseUtils.getApiClass(tableName)+";");
        list.add("import org.springframework.cloud.openfeign.FeignClient;\n");
        list.add("@FeignClient(\""+getUrl(tableName)+"\")");
        list.add("public interface "+ DatabaseUtils.getDTOName(tableName)+NameContant.DATABASE_API+" extends "+DatabaseUtils.getApiClass(tableName)+"{");
        list.add("}");
        return list;
    }
    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getFeignClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "hub"+ File.separator+"server"+ File.separator+"remote";
    }
}
