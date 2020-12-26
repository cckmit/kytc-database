package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kytc.database.dao.dto.ColumnDTO;
import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.dto.AnalyzerDTO;
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
public class ReactDetailAnalyzer implements Analyzer{
    @Autowired
    public ReactDetailAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }

    @Override
    public List<String> analyzer(AnalyzerDTO analyzerDTO) {
        List<ColumnResponse> columnResponses = analyzerDTO.getColumnResponses();
        String tableName = analyzerDTO.getTableName();
        String description = analyzerDTO.getDescription();
        List<String> list = new ArrayList<>();
        list.add("import React from 'react';\n");
        list.add("import { Form,Descriptions, Modal} from 'antd';\n");
        list.add("@Form.create()");
        list.add("class "+ DatabaseUtils.getDTOName(tableName)+"DetailComponent extends React.Component {\n" +
                DatabaseUtils.getTabs(1)+"render() {\n" +
                DatabaseUtils.getTabs(2)+"const { visible, record, onCancel } = this.props;\n" +
                DatabaseUtils.getTabs(2)+"return (\n" +
                DatabaseUtils.getTabs(3)+"<Modal\n" +
                DatabaseUtils.getTabs(4)+"visible={visible}\n" +
                DatabaseUtils.getTabs(4)+"title={`"+description+"详情`}\n" +
                DatabaseUtils.getTabs(4)+"width={600}\n" +
                DatabaseUtils.getTabs(4)+"footer={null}\n" +
                DatabaseUtils.getTabs(4)+"onCancel={onCancel}>\n" +
                DatabaseUtils.getTabs(4)+"<Descriptions bordered border column={2} size=\"small\">");
        for(ColumnResponse columnResponse:columnResponses){
            String name = DatabaseUtils.getJavaName(columnResponse.getColumnName());
            if(Arrays.asList("lastUpdatedAt").contains(name)){
                continue;
            }
            String comment = (""+columnResponse.getColumnComment()).trim()+" "+name;
            comment = comment.trim();
            if(comment.contains(" ")){
                comment = comment.substring(0,comment.indexOf(" "));
            }
            list.add(DatabaseUtils.getTabs(5)+"<Descriptions.Item label=\""+comment+"\">{record==null?null:record."+name+"}</Descriptions.Item>");
        }
        list.add(DatabaseUtils.getTabs(4)+"</Descriptions>\n"+
                DatabaseUtils.getTabs(3)+"</Modal>\n" +
                DatabaseUtils.getTabs(2)+");\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                "}\n" +
                "export default "+ DatabaseUtils.getDTOName(tableName)+"DetailComponent;");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return "detail.js";
    }

    @Override
    public String getPackage() {
        return "react";
    }
}
