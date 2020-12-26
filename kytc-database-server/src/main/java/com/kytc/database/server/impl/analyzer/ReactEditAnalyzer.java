package com.kytc.database.server.impl.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.dto.AnalyzerDTO;
import com.kytc.database.server.helper.AnalyzerHelper;
import com.kytc.database.server.service.ayalyzer.Analyzer;
import com.kytc.database.server.utils.DatabaseUtils;
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
public class ReactEditAnalyzer implements Analyzer{
    @Autowired
    public ReactEditAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    private String getLoading(String tableName){
        if(tableName.toLowerCase().startsWith("t_")){
            tableName = tableName.substring(2).toLowerCase();
        }
        return tableName;
    }
    @Override
    public List<String> analyzer(AnalyzerDTO analyzerDTO) {
        List<ColumnResponse> columnResponses = analyzerDTO.getColumnResponses();
        String tableName = analyzerDTO.getTableName();
        List<String> list = new ArrayList<>();
        list.add("import React from 'react';\n");
        list.add("import { Form, Input, Select,Modal, Row,Col} from 'antd';\n");
        list.add("import { connect } from 'dva';\n");
        list.add("@Form.create()\n" +
                "@connect(({ "+getLoading(tableName)+", loading }) => ({\n" +
                DatabaseUtils.getTabs(1)+getLoading(tableName)+": "+getLoading(tableName)+",\n" +
                DatabaseUtils.getTabs(1)+"loading: loading.models."+getLoading(tableName)+",\n" +
                "}))");
        list.add("class "+ DatabaseUtils.getDTOName(tableName)+"EditComponent extends React.Component {\n" +
                DatabaseUtils.getTabs(1)+"constructor(props) {\n" +
                DatabaseUtils.getTabs(2)+"super(props);\n" +
                DatabaseUtils.getTabs(2)+"this.state = {\n" +
                DatabaseUtils.getTabs(3)+"onSuccess:null\n" +
                DatabaseUtils.getTabs(2)+"}\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                DatabaseUtils.getTabs(1)+"onCreated = () => {\n" +
                DatabaseUtils.getTabs(2)+"const form = this.props.form;\n" +
                DatabaseUtils.getTabs(2)+"const { dispatch, onSuccess } = this.props;\n" +
                DatabaseUtils.getTabs(2)+"form.validateFields((err, values) => {\n" +
                DatabaseUtils.getTabs(3)+"if (err) {\n" +
                DatabaseUtils.getTabs(4)+"return;\n" +
                DatabaseUtils.getTabs(3)+"}\n" +
                DatabaseUtils.getTabs(3)+"var that = this;\n" +
                DatabaseUtils.getTabs(3)+"if(values.id==null){\n" +
                DatabaseUtils.getTabs(4)+"dispatch({\n" +
                DatabaseUtils.getTabs(5)+"type: \""+getLoading(tableName)+"/add"+ DatabaseUtils.getDTOName(tableName)+"\",\n" +
                DatabaseUtils.getTabs(5)+"payload:{...values},\n" +
                DatabaseUtils.getTabs(5)+"callback:()=>{\n" +
                DatabaseUtils.getTabs(6)+"onSuccess && onSuccess();\n" +
                DatabaseUtils.getTabs(5)+"}\n" +
                DatabaseUtils.getTabs(4)+"})\n" +
                DatabaseUtils.getTabs(3)+"}else{\n" +
                DatabaseUtils.getTabs(4)+"dispatch({\n" +
                DatabaseUtils.getTabs(5)+"type: \""+getLoading(tableName)+"/edit"+ DatabaseUtils.getDTOName(tableName)+"\",\n" +
                DatabaseUtils.getTabs(5)+"payload:{...values},\n" +
                DatabaseUtils.getTabs(5)+"callback:()=>{\n" +
                DatabaseUtils.getTabs(6)+"onSuccess && onSuccess();\n" +
                DatabaseUtils.getTabs(5)+"}\n" +
                DatabaseUtils.getTabs(4)+"})\n" +
                DatabaseUtils.getTabs(3)+"}\n" +
                DatabaseUtils.getTabs(2)+"})\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                DatabaseUtils.getTabs(1)+"render() {\n" +
                DatabaseUtils.getTabs(2)+"const { visible, form, record, onCancel,onSuccess } = this.props;\n" +
                DatabaseUtils.getTabs(2)+"const { getFieldDecorator } = form;\n" +
                DatabaseUtils.getTabs(2)+"const FormItemLayout = {\n" +
                DatabaseUtils.getTabs(3)+"labelCol: {\n" +
                DatabaseUtils.getTabs(4)+"xs: { span: 24 },\n" +
                DatabaseUtils.getTabs(4)+"sm: { span: 8 },\n" +
                DatabaseUtils.getTabs(3)+"},\n" +
                DatabaseUtils.getTabs(3)+"wrapperCol: {\n" +
                DatabaseUtils.getTabs(4)+"xs: { span: 24 },\n" +
                DatabaseUtils.getTabs(4)+"sm: { span: 16 },\n" +
                DatabaseUtils.getTabs(3)+"},\n" +
                DatabaseUtils.getTabs(2)+"};\n" +
                DatabaseUtils.getTabs(2)+"return (\n" +
                DatabaseUtils.getTabs(3)+"<Modal\n" +
                DatabaseUtils.getTabs(4)+"visible={visible}\n" +
                DatabaseUtils.getTabs(4)+"title={record==null?\"新增\":\"修改\"}\n" +
                DatabaseUtils.getTabs(4)+"okText=\"确认\"\n" +
                DatabaseUtils.getTabs(4)+"width={600}\n" +
                DatabaseUtils.getTabs(4)+"onCancel={onCancel}\n" +
                DatabaseUtils.getTabs(4)+"onOk={this.onCreated}>\n" +
                DatabaseUtils.getTabs(4)+"<Form {...FormItemLayout}>\n" +
                DatabaseUtils.getTabs(5)+"<Form.Item>\n" +
                DatabaseUtils.getTabs(6)+"{getFieldDecorator('id', {\n" +
                DatabaseUtils.getTabs(7)+"initialValue: record ? record.id:null\n" +
                DatabaseUtils.getTabs(6)+"})(<Input type=\"hidden\"/>)}\n" +
                DatabaseUtils.getTabs(5)+"</Form.Item>\n" +
                DatabaseUtils.getTabs(5)+"<Row>");
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
            list.add(DatabaseUtils.getTabs(6)+"<Col sm={12}>\n" +
                    DatabaseUtils.getTabs(7)+"<Form.Item label=\""+comment+"\">\n" +
                    DatabaseUtils.getTabs(8)+"{getFieldDecorator('"+name+"',{\n" +
                    DatabaseUtils.getTabs(9)+"initialValue:record ? record."+name+":'',\n" +
                    DatabaseUtils.getTabs(8)+"})(\n" +
                    DatabaseUtils.getTabs(8)+"<Input/>)}\n" +
                    DatabaseUtils.getTabs(7)+"</Form.Item>\n" +
                    DatabaseUtils.getTabs(6)+"</Col>");
        }
        list.add(DatabaseUtils.getTabs(5)+"</Row>\n" +
                DatabaseUtils.getTabs(4)+"</Form>\n" +
                DatabaseUtils.getTabs(3)+"</Modal>\n" +
                DatabaseUtils.getTabs(2)+");\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                "}\n" +
                "export default "+ DatabaseUtils.getDTOName(tableName)+"EditComponent;");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return "edit.js";
    }

    @Override
    public String getPackage() {
        return "react";
    }
}
