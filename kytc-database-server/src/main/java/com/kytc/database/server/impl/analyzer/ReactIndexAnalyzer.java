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
public class ReactIndexAnalyzer implements Analyzer{
    @Autowired
    public ReactIndexAnalyzer(AnalyzerHelper analyzerHelper){
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
        String loading = getLoading(tableName);
        String dtoName = DatabaseUtils.getDTOName(tableName);
        List<String> list = new ArrayList<>();
        list.add("import React, { Fragment } from 'react';\n");
        list.add("import { connect } from 'dva';\n");
        list.add("import { Link } from 'umi';\n");
        list.add("import { Card, Icon,Button,Modal,Divider,Table } from 'antd';\n");
        list.add("import "+ DatabaseUtils.getDTOName(tableName)+"SearchForm from './Search';\n");

        list.add("@connect(({ "+loading+", loading }) => ({");
        list.add("\t"+loading+": "+loading+",");
        list.add("\tloading: loading.models."+loading+",");
        list.add("}))");

        list.add("export default class "+dtoName+"Page extends React.Component {\n" +
                "\tconstructor(props) {\n" +
                "\t\tsuper(props);\n" +
                "\t\tthis.state = {\n" +
                "\t\t\tvisible: false,\n" +
                "\t\t\trecord: null,\n" +
                "\t\t\tsearchForm:null,\n" +
                "\t\t\tdetailVisible:false\n" +
                "\t\t}\n" +
                "\t}");

        list.add(DatabaseUtils.getTabs(1)+"handleSearchData = form => {\n" +
                DatabaseUtils.getTabs(2)+"const { dispatch, "+loading+",searchForm } = this.props;\n" +
                DatabaseUtils.getTabs(2)+"dispatch({\n" +
                DatabaseUtils.getTabs(3)+"type: '"+loading+"/query"+dtoName+"s',\n" +
                DatabaseUtils.getTabs(3)+"payload: {");

        for(ColumnResponse columnResponse:columnResponses){
            String name = columnResponse.getJavaName();
            if(Arrays.asList("id","createdAt","createdBy","updatedAt","updatedBy","lastUpdatedAt").contains(name)){
                continue;
            }
            if(columnResponse.getColumnType().toLowerCase().contains("text")){
                continue;
            }
            list.add(DatabaseUtils.getTabs(4)+ columnResponse.getJavaName().toLowerCase()+": form.getFieldValue('"+
                    columnResponse.getJavaName()+"'), ");
        }

        list.add(DatabaseUtils.getTabs(4)+"pageindex: 0,\n" +
                DatabaseUtils.getTabs(4)+"pagesize: 10,\n" +
                DatabaseUtils.getTabs(3)+"},\n" +
                DatabaseUtils.getTabs(2)+"});\n" +
                DatabaseUtils.getTabs(1)+"}");

        list.add(DatabaseUtils.getTabs(1)+"handle"+dtoName+"Detail = (e, record) => {\n" +
                DatabaseUtils.getTabs(2)+"e.preventDefault();\n" +
                DatabaseUtils.getTabs(2)+"this.setState({\n" +
                DatabaseUtils.getTabs(3)+"detailVisible: true,\n" +
                DatabaseUtils.getTabs(3)+"record: record,\n" +
                DatabaseUtils.getTabs(2)+"})\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                "\n" +
                DatabaseUtils.getTabs(1)+"handleEditSuccess = () =>{\n" +
                DatabaseUtils.getTabs(2)+"this.setState({\n" +
                DatabaseUtils.getTabs(3)+"visible: false,\n" +
                DatabaseUtils.getTabs(2)+ "})\n" +
                DatabaseUtils.getTabs(2)+"this.handleSearchData(this.state.searchForm);\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                "\n" +
                DatabaseUtils.getTabs(1)+"onCancel = () => {\n" +
                DatabaseUtils.getTabs(2)+"this.setState({ visible: false });\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                DatabaseUtils.getTabs(1)+"saveFormRef = formRef => {\n" +
                DatabaseUtils.getTabs(2)+"this.formRef = formRef;\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                DatabaseUtils.getTabs(1)+"handle"+dtoName+" = (e,record)=>{\n" +
                DatabaseUtils.getTabs(2)+"e.preventDefault();\n" +
                DatabaseUtils.getTabs(2)+"this.setState({\n" +
                DatabaseUtils.getTabs(3)+"visible: true,\n" +
                DatabaseUtils.getTabs(3)+"record: record\n" +
                DatabaseUtils.getTabs(2)+"})\n" +
                DatabaseUtils.getTabs(1)+"}");

        list.add(DatabaseUtils.getTabs(1)+"handleCancel"+dtoName+"Detail = (e,record)=>{\n" +
                DatabaseUtils.getTabs(2)+"e.preventDefault();\n" +
                DatabaseUtils.getTabs(2)+"this.setState({\n" +
                DatabaseUtils.getTabs(3)+"detailVisible: false\n" +
                DatabaseUtils.getTabs(2)+"})\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                DatabaseUtils.getTabs(1)+"delete"+dtoName+" = (e,record)=>{\n" +
                DatabaseUtils.getTabs(2)+"e.preventDefault();\n" +
                DatabaseUtils.getTabs(2)+"const { dispatch,"+loading+": {data} } = this.props;\n" +
                DatabaseUtils.getTabs(2)+"const that = this;\n" +
                DatabaseUtils.getTabs(2)+"Modal.confirm({\n" +
                DatabaseUtils.getTabs(3)+"title: '删除',\n" +
                DatabaseUtils.getTabs(3)+"content: `确定要删除\"${record.id}\"吗?`,\n" +
                DatabaseUtils.getTabs(3)+"onOk() {\n" +
                DatabaseUtils.getTabs(4)+"dispatch({\n" +
                DatabaseUtils.getTabs(5)+"type: \""+loading+"/delete"+dtoName+"\",\n" +
                DatabaseUtils.getTabs(5)+"payload:record.id,\n" +
                DatabaseUtils.getTabs(5)+"callback:()=>{\n" +
                DatabaseUtils.getTabs(6)+"that.handleSearchData(that.state.searchForm);\n" +
                DatabaseUtils.getTabs(5)+"}\n" +
                DatabaseUtils.getTabs(4)+"})\n" +
                DatabaseUtils.getTabs(3)+"},\n" +
                DatabaseUtils.getTabs(3)+"onCancel() {\n" +
                DatabaseUtils.getTabs(3)+"},\n" +
                DatabaseUtils.getTabs(2)+"});\n" +
                DatabaseUtils.getTabs(1)+"}");


        list.add(DatabaseUtils.getTabs(1)+"render() {\n" +
                DatabaseUtils.getTabs(2)+"const { "+loading+": { data,pageindex, pagesize,pagination } } = this.props;\n" +
                DatabaseUtils.getTabs(2)+"const columns = [");
        for(ColumnResponse columnResponse:columnResponses){
            if(columnResponse.getColumnType().toLowerCase().contains("text")){
                continue;
            }
            if(Arrays.asList("id","createdAt","createdBy","updatedAt","updatedBy","lastUpdatedAt","isDeleted").contains(columnResponse.getJavaName())){
                continue;
            }
            String name = columnResponse.getJavaName();
            String comment = (""+columnResponse.getColumnComment()).trim()+" "+name;
            comment = comment.trim();
            if(comment.contains(" ")){
                comment = comment.substring(0,comment.indexOf(" "));
            }
            list.add(DatabaseUtils.getTabs(3)+"{");
            list.add(DatabaseUtils.getTabs(4)+"title: '"+comment+"',");
            list.add(DatabaseUtils.getTabs(4)+"dataIndex: '"+name+"',");
            list.add(DatabaseUtils.getTabs(3)+"},");
        }
        list.add(DatabaseUtils.getTabs(3)+"{");
        list.add(DatabaseUtils.getTabs(4)+"title:'操作',");
        list.add(DatabaseUtils.getTabs(4)+"width:160,");
        list.add(DatabaseUtils.getTabs(4)+"render:(record) => {");
        list.add(DatabaseUtils.getTabs(5)+"return (");
        list.add(DatabaseUtils.getTabs(6)+"<div>");
        list.add(DatabaseUtils.getTabs(7)+"<Link to=\"\" onClick={(e) => this.handle"+dtoName+"(e,record)}><Icon type=\"edit\"/>修改</Link>");
        list.add(DatabaseUtils.getTabs(7)+"<Divider type=\"vertical\" />");
        list.add(DatabaseUtils.getTabs(7)+"<Link to=\"\" onClick={(e) => this.delete"+dtoName+"(e,record)}><Icon type=\"delete\"/>删除</Link>");
        list.add(DatabaseUtils.getTabs(7)+"<Divider type=\"vertical\" />");
        list.add(DatabaseUtils.getTabs(7)+"<Link to=\"\" onClick={(e) => this.handle"+dtoName+"Detail(e,record)}><Icon type=\"profile\"/>详情</Link>");
        list.add(DatabaseUtils.getTabs(6)+"</div>");
        list.add(DatabaseUtils.getTabs(5)+");");
        list.add(DatabaseUtils.getTabs(4)+"}");
        list.add(DatabaseUtils.getTabs(3)+"}");
        list.add(DatabaseUtils.getTabs(2)+"];");
        list.add(DatabaseUtils.getTabs(1)+"return (");
        list.add(DatabaseUtils.getTabs(2)+"<Fragment>");
        list.add(DatabaseUtils.getTabs(3)+"<Card>");
        list.add(DatabaseUtils.getTabs(4)+"<"+dtoName+"SearchForm handleSearchData={this.handleSearchData}/>");
        list.add(DatabaseUtils.getTabs(4)+"<Button");
        list.add(DatabaseUtils.getTabs(5)+"style={{marginBottom:\"10px\"}}");
        list.add(DatabaseUtils.getTabs(5)+"type=\"primary\"");
        list.add(DatabaseUtils.getTabs(5)+"icon=\"plus\"");
        list.add(DatabaseUtils.getTabs(5)+"onClick={(e) => this.handle"+dtoName+"(e,null)}");
        list.add(DatabaseUtils.getTabs(5)+">添加</Button>");
        list.add(DatabaseUtils.getTabs(4)+"<Table rowKey=\"id\"");
        list.add(DatabaseUtils.getTabs(5)+"pagination={{");
        list.add(DatabaseUtils.getTabs(6)+"current: data.pageindex,");
        list.add(DatabaseUtils.getTabs(6)+"total: data.total,");
        list.add(DatabaseUtils.getTabs(6)+"pageSize: data.pagesize,");
        list.add(DatabaseUtils.getTabs(5)+"}}");
        list.add(DatabaseUtils.getTabs(5)+"dataSource={data.rows}");
        list.add(DatabaseUtils.getTabs(5)+"columns={columns}");
        list.add(DatabaseUtils.getTabs(5)+"loading={this.props.loading}/>");
        list.add(DatabaseUtils.getTabs(4)+"</Card>");
        list.add(DatabaseUtils.getTabs(4)+"<"+dtoName+"EditComponent");
        list.add(DatabaseUtils.getTabs(5)+"wrappedComponentRef={this.saveFormRef}");
        list.add(DatabaseUtils.getTabs(5)+"record={this.state.record}");
        list.add(DatabaseUtils.getTabs(5)+"visible={this.state.visible}");
        list.add(DatabaseUtils.getTabs(5)+"onCancel = {this.onCancel}");
        list.add(DatabaseUtils.getTabs(5)+"onSuccess = {this.handleEditSuccess}");
        list.add(DatabaseUtils.getTabs(4)+"></"+dtoName+"EditComponent>");
        list.add(DatabaseUtils.getTabs(4)+"<"+dtoName+"DetailComponent visible={this.state.detailVisible} record={this.state.record} onCancel={this.handleCancel"+dtoName+"Detail}></"+dtoName+"DetailComponent>");
        list.add(DatabaseUtils.getTabs(3)+"</Fragment>");
        list.add(DatabaseUtils.getTabs(2)+");");
        list.add(DatabaseUtils.getTabs(1)+"}");
        list.add("}");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return "index.js";
    }

    @Override
    public String getPackage() {
        return "react";
    }
}
