package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.kytc.database.dao.dto.ColumnDTO;
import com.kytc.database.response.ColumnResponse;
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
public class ReactServiceAnalyzer implements Analyzer{
    @Autowired
    public ReactServiceAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    private String getLoading(String tableName){
        if(tableName.toLowerCase().startsWith("t_")){
            tableName = tableName.substring(2).toLowerCase();
        }
        return tableName;
    }

    @Override
    public List<String> analyzer(String pkg,String tableName, List<ColumnResponse> columnResponses,String description) {
        String loading = getLoading(tableName);
        String dtoName = DatabaseUtils.getDTOName(tableName);
        List<String> list = new ArrayList<>();
        list.add("import request from '@/utils/request';\n" +
                "import { stringify } from 'qs';\n" +
                "\n" +
                "export async function query"+dtoName+"s(params) {\n" +
                DatabaseUtils.getTabs(1)+"return request(`/api/"+loading+"/infos?${stringify(params)}`)\n" +
                "}\n" +
                "\n" +
                "export async function add"+dtoName+"(params) {\n" +
                DatabaseUtils.getTabs(1)+"return request(`/api/"+loading+"/info`, {\n" +
                DatabaseUtils.getTabs(2)+"method: 'POST',\n" +
                DatabaseUtils.getTabs(2)+"body: JSON.stringify(params),\n" +
                DatabaseUtils.getTabs(1)+"});\n" +
                "}\n" +
                "\n" +
                "export async function edit"+dtoName+"(params) {\n" +
                DatabaseUtils.getTabs(1)+"return request(`/api/"+loading+"/info`, {\n" +
                DatabaseUtils.getTabs(2)+"method: 'PUT',\n" +
                DatabaseUtils.getTabs(2)+"body: JSON.stringify(params),\n" +
                DatabaseUtils.getTabs(1)+"});\n" +
                "}\n" +
                "\n" +
                "export async function delete"+dtoName+"(id) {\n" +
                DatabaseUtils.getTabs(1)+"return request(`/api/"+loading+"/`+id+`/info`, {\n" +
                DatabaseUtils.getTabs(2)+"method: 'delete'\n" +
                DatabaseUtils.getTabs(1)+"});\n" +
                "}");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return "service.js";
    }

    @Override
    public String getPackage() {
        return "react";
    }
}
