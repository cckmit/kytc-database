package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kytc.database.response.ColumnResponse;
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
public class ReactSearchAnalyzer implements Analyzer{
    @Autowired
    public ReactSearchAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    private String getLoading(String tableName){
        if(tableName.toLowerCase().startsWith("t_")){
            tableName = tableName.substring(2).toLowerCase();
        }
        return tableName;
    }
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,
                                 Map<Boolean, Map<String,List<ColumnIndexDTO>>> columnMap, String description) {
        List<String> list = new ArrayList<>();
        list.add("import React from 'react';\n");
        list.add("import { Card, Form, Input, Select, Icon,Button,Dropdown, InputNumber,Modal,message,Divider,Table, Row,Col} from 'antd';\n");
        list.add("@Form.create()");
        list.add("class "+ DatabaseUtils.getDTOName(tableName)+"SearchForm extends React.Component{");
        list.add(DatabaseUtils.getTabs(1)+"componentDidMount() {");

        list.add(DatabaseUtils.getTabs(2)+"const {\n" +
                DatabaseUtils.getTabs(3)+"form,\n" +
                DatabaseUtils.getTabs(3)+"handleSearchData,\n" +
                DatabaseUtils.getTabs(2)+"} = this.props;\n" +
                DatabaseUtils.getTabs(2)+"handleSearchData && handleSearchData(form);\n" +
                DatabaseUtils.getTabs(1)+"}");
        list.add(DatabaseUtils.getTabs(1)+"handleSearch = e => {\n" +
                DatabaseUtils.getTabs(2)+"const { form, handleSearchData } = this.props;\n" +
                DatabaseUtils.getTabs(2)+"e.preventDefault();\n" +
                DatabaseUtils.getTabs(2)+"form.validateFields((err, fieldsValue) => {\n" +
                DatabaseUtils.getTabs(3)+"if (err) {\n" +
                DatabaseUtils.getTabs(4)+"return;\n" +
                DatabaseUtils.getTabs(3)+"}\n" +
                DatabaseUtils.getTabs(3)+"handleSearchData && handleSearchData(form);\n" +
                DatabaseUtils.getTabs(2)+"});\n" +
                DatabaseUtils.getTabs(1)+"};");
        list.add(DatabaseUtils.getTabs(1)+"handleFormReset = () => {\n" +
                DatabaseUtils.getTabs(2)+"const { form, dispatch, handleSearchData } = this.props;\n" +
                DatabaseUtils.getTabs(2)+"form.resetFields();\n" +
                DatabaseUtils.getTabs(2)+"handleSearchData && handleSearchData(form);\n" +
                DatabaseUtils.getTabs(1)+"};");
        list.add(DatabaseUtils.getTabs(1)+"render() {\n" +
                DatabaseUtils.getTabs(2)+"const { getFieldDecorator } = this.props.form;");

        list.add(DatabaseUtils.getTabs(2)+"return (<Form layout=\"inline\" onSubmit={this.handleSearch}>");
        list.add(DatabaseUtils.getTabs(3)+"<Row gutter={{ md: 8, lg: 24, xl: 48 }}>");

        for(ColumnResponse columnResponse:columnResponses){
            String name = DatabaseUtils.getJavaName(columnResponse.getColumnName());
            if(Arrays.asList("id","createdAt","createdBy","updatedAt","updatedBy","lastUpdatedAt").contains(name)){
                continue;
            }
            String comment = (""+columnResponse.getColumnComment()).trim()+" "+name;
            comment = comment.trim();
            if(comment.contains(" ")){
                comment = comment.substring(0,comment.indexOf(" "));
            }
            list.add(DatabaseUtils.getTabs(4)+"<Col md={8} sm={24}>\n" +
                    DatabaseUtils.getTabs(5)+"<Form.Item label=\""+comment+"\">\n" +
                    DatabaseUtils.getTabs(6)+"{getFieldDecorator('"+name+"',{\n" +
                    DatabaseUtils.getTabs(7)+"initialValue:''\n" +
                    DatabaseUtils.getTabs(6)+"})(\n" +
                    DatabaseUtils.getTabs(6)+"<Input/>)}\n" +
                    DatabaseUtils.getTabs(5)+"</Form.Item>\n" +
                    DatabaseUtils.getTabs(4)+"</Col>");
        }
        list.add(DatabaseUtils.getTabs(4)+"<Col md={8} sm={24}>\n" +
                DatabaseUtils.getTabs(5)+"<span>\n" +
                DatabaseUtils.getTabs(6)+"<Button type=\"primary\" htmlType=\"submit\">\n" +
                DatabaseUtils.getTabs(7)+"查询\n" +
                DatabaseUtils.getTabs(6)+"</Button>\n" +
                DatabaseUtils.getTabs(6)+"<Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>\n" +
                DatabaseUtils.getTabs(7)+"重置\n" +
                DatabaseUtils.getTabs(6)+"</Button>\n" +
                DatabaseUtils.getTabs(5)+"</span>\n" +
                DatabaseUtils.getTabs(4)+"</Col>");

        list.add(DatabaseUtils.getTabs(3)+"</Row>\n" +
                DatabaseUtils.getTabs(2)+"</Form>)\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                "}\n\n" +
                "export default "+ DatabaseUtils.getDTOName(tableName)+"SearchForm;");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return "search.js";
    }

    @Override
    public String getPackage() {
        return "react";
    }
}
