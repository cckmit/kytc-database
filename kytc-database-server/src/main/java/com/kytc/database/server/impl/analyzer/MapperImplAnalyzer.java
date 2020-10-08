package com.kytc.database.server.impl.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kytc.database.response.ColumnResponse;
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
 * @date: 2019-08-24 22:39
 * @see <a target="_blank" href="">参考文档</a>
 **/
@Component
@Slf4j
public class MapperImplAnalyzer implements Analyzer{
    @Autowired
    public MapperImplAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,String description) {
        List<String> list = new ArrayList<>();
        list.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\""+pkg+".dao.mapper."+DatabaseUtils.getMapperClass(tableName)+"\">");
        list.add("\t<resultMap id=\"BaseResultMap\" type=\""+pkg+".dao.data."+DatabaseUtils.getDataClass(tableName)+"\">");
        ColumnResponse priColumn = null;
        for(ColumnResponse columnResponse:columnResponses) {
            if( DatabaseUtils.isPriKey(columnResponse) ){
                priColumn = columnResponse;
            }
            if(DatabaseUtils.isAutoIncrement(columnResponse)){
                list.add("\t\t<id column=\""+columnResponse.getColumnName()+"\" jdbcType=\"BIGINT\" property=\""+DatabaseUtils.getJavaName(columnResponse.getColumnName())+"\" />");
                continue;
            }
            if(DatabaseUtils.isIgnore(columnResponse)){
                continue;
            }
            list.add("\t\t<result column=\""+columnResponse.getColumnName()+"\" jdbcType=\""+ DatabaseUtils.getMapperDataType(columnResponse.getDataType())+"\" property=\""+DatabaseUtils.getJavaName(columnResponse.getColumnName())+"\" />");
        }
        list.add("\t</resultMap>");
        list.add("\t<sql id=\"Base_Column_List\">");
        String line = "";
        for(ColumnResponse columnResponse:columnResponses) {
            if(!DatabaseUtils.isIgnore(columnResponse)){
                line = line.concat(","+columnResponse.getColumnName());
            }
        }
        line = line.substring(1);
        list.add("\t\t"+line);
        list.add("\t</sql>");
        list.add("\t<select id=\"selectByPrimaryKey\" parameterType=\"java.lang.Long\" resultMap=\"BaseResultMap\">\n" +
                "\t\tselect \n" +
                "\t\t<include refid=\"Base_Column_List\" />\n" +
                "\t\tfrom "+tableName+"\n" +
                "\t\twhere id = #{id,jdbcType=BIGINT}\n" +
                "\t</select>");
        if( null != priColumn ){
            list.add("\t<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang.Long\">\n" +
                    "\t\tdelete from "+tableName+"\n" +
                    "\t\twhere "+priColumn.getColumnName()+" = #{"+DatabaseUtils.getJavaName(priColumn.getColumnName())+",jdbcType="+DatabaseUtils.getMapperDataType(priColumn.getDataType())+"}\n" +
                    "\t</delete>");
        }

        list.add("\t<insert id=\"insert\" parameterType=\""+pkg+".dao.data."+DatabaseUtils.getDataClass(tableName)+"\">\n" +
                "\t\tinsert into "+tableName+" (");
        list.add("\t\t\t"+line);
        list.add("\t\t) \n\t\tvalues (");
        String column = "";
        for(ColumnResponse columnResponse:columnResponses) {
            if(!DatabaseUtils.isIgnore(columnResponse)){
                column +=",#{"+DatabaseUtils.getJavaName(columnResponse.getColumnName())+",jdbcType="+DatabaseUtils.getMapperDataType(columnResponse.getDataType())+"}";
            }
        }
        column = column.substring(1);
        list.add("\t\t\t"+column);
        list.add("\t\t)\n\t</insert>");
        list.add("\t<insert id=\"insertSelective\" parameterType=\""+pkg+".dao.data."+DatabaseUtils.getDataClass(tableName)+"\">\n" +
                "\t\tinsert into "+tableName);
        list.add("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for(ColumnResponse columnResponse:columnResponses) {
            if(!DatabaseUtils.isIgnore(columnResponse)){
                list.add("\t\t\t<if test=\""+DatabaseUtils.getJavaName(columnResponse.getColumnName())+" != null\">");
                list.add("\t\t\t\t"+columnResponse.getColumnName()+",");
                list.add("\t\t\t</if>");
            }
        }
        list.add("\t\t</trim>");
        list.add("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
        for(ColumnResponse columnResponse:columnResponses) {
            if(!DatabaseUtils.isIgnore(columnResponse)) {
                list.add("\t\t\t<if test=\"" + DatabaseUtils.getJavaName(columnResponse.getColumnName()) + " != null\">");
                list.add("\t\t\t\t#{" + DatabaseUtils.getJavaName(columnResponse.getColumnName()) + ",jdbcType=" + DatabaseUtils.getMapperDataType(columnResponse.getDataType()) + "},");
                list.add("\t\t\t</if>");
            }
        }
        list.add("\t\t</trim>\n\t</insert>");
        list.add("\t<update id=\"updateByPrimaryKeySelective\" parameterType=\""+pkg+".dao.data."+DatabaseUtils.getDataClass(tableName)+"\">\n" +
                "\t\tupdate "+tableName+"");
        list.add("\t\t<set>");
        for(ColumnResponse columnResponse:columnResponses) {
            if(!DatabaseUtils.isIgnore(columnResponse)) {
                list.add("\t\t\t<if test=\"" + DatabaseUtils.getJavaName(columnResponse.getColumnName()) + " != null\">");
                list.add("\t\t\t\t" + columnResponse.getColumnName() + " = #{" + DatabaseUtils.getJavaName(columnResponse.getColumnName()) + ",jdbcType=" + DatabaseUtils.getMapperDataType(columnResponse.getDataType()) + "},");
                list.add("\t\t\t</if>");
            }
        }
        list.add("\t\t</set>");
        list.add("\t\t<where>id = #{id,jdbcType=BIGINT}</where>");
        list.add("\t</update>");
        list.add("\t<update id=\"updateByPrimaryKey\" parameterType=\""+pkg+".dao.data."+DatabaseUtils.getDataClass(tableName)+"\">\n" +
                "\t\tupdate "+tableName+"");
        list.add("\t\tset ");
        for(ColumnResponse columnResponse:columnResponses) {
            if(!DatabaseUtils.isIgnore(columnResponse)) {
                list.add("\t\t\t" + columnResponse.getColumnName() + " = #{" + DatabaseUtils.getJavaName(columnResponse.getColumnName()) + ",jdbcType=" + DatabaseUtils.getMapperDataType(columnResponse.getDataType()) + "},");
            }
        }
        String lastLine = list.get(list.size()-1);
        lastLine = lastLine.substring(0,lastLine.length()-1);
        list.remove(list.size()-1);
        list.add(lastLine);
        list.add("\t\twhere "+priColumn.getColumnName()+" = #{"+DatabaseUtils.getJavaName(priColumn.getColumnName())+",jdbcType="+DatabaseUtils.getMapperDataType(priColumn.getDataType())+"}");
        list.add("  </update>\n" +
                "</mapper>");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getMapperClass(tableName)+".xml";
    }

    @Override
    public String getPackage() {
        return "xml";
    }
}
