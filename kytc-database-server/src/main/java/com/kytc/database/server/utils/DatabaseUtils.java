package com.kytc.database.server.utils;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.config.NameContant;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <a style="display:none">简单描述</a>.
 * <a style="display:none">详细描述</a><p></p>
 * <p><strong>目的:</strong></p>
 * <p><strong>原因:</strong></p>
 * <p><strong>用途:</strong></p>
 *
 * @author: 何志同
 * @date: 2019-08-24 22:06
 * @see <a target="_blank" href="">参考文档</a>
 **/
public class DatabaseUtils {
    public static String getDTOName(String tableName){
        if(tableName.startsWith("t_")){
            tableName = tableName.substring(2);
        }
        String[] tableNameArr = tableName.split("_");
        StringBuffer stringBuffer = new StringBuffer();
        for(String name:tableNameArr){
            stringBuffer.append(getName(name));
        }
        return stringBuffer.toString();
    }

    public static boolean isPriKey(ColumnResponse columnResponse){
        if( null == columnResponse ){
            return false;
        }
        if( "PRI".equalsIgnoreCase(columnResponse.getColumnKey()) ){
            return true;
        }
        return false;
    }

    public static boolean isAutoIncrement(ColumnResponse columnResponse){
        return isPriKey(columnResponse) && "auto_increment".equalsIgnoreCase(columnResponse.getExtra());
    }

    public static boolean isIgnore(ColumnResponse columnResponse){
        return "on update CURRENT_TIMESTAMP".equalsIgnoreCase(columnResponse.getExtra());
    }

    public static ColumnResponse getPriColumn(List<ColumnResponse> columnResponses){
        for( ColumnResponse columnResponse : columnResponses ){
            if( isPriKey(columnResponse) ){
                return columnResponse;
            }
        }
        return null;
    }

    private static String getName(String name){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(name.substring(0,1).toUpperCase()).append(name.substring(1));
        return stringBuffer.toString();
    }

    public static String getJavaType(String jdbcType){
        String type = jdbcType;
        if(jdbcType.startsWith("bigint")){
            return "Long";
        }else if(jdbcType.startsWith("varchar")){
            return "String";
        } else if (jdbcType.startsWith("int")) {
            return "Integer";
        }else if(jdbcType.startsWith("datetime")||jdbcType.startsWith("date")){
            return "Date";
        }else if(jdbcType.startsWith("text")){
            return "String";
        }else if(jdbcType.startsWith("tinyint")||jdbcType.startsWith("smallint")){
            return "Integer";
        }else if(jdbcType.startsWith("bit")){
            return "Boolean";
        }else if(jdbcType.startsWith("char")){
            return "String";
        }
        return type;
    }

    public static String getJavaName(String columnName){
        String[] columnNameArr = columnName.split("_");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(columnNameArr[0]);
        for(int i=1;i<columnNameArr.length;i++){
            stringBuffer.append(getName(columnNameArr[i]));
        }
        return stringBuffer.toString();
    }

    public static boolean isRealDelete(List<ColumnResponse> list){
        if(CollectionUtils.isEmpty(list)){
            return true;
        }
        for( ColumnResponse columnResponse : list ){
            if("is_deleted".equalsIgnoreCase(columnResponse.getColumnName())){
                return false;
            }
        }
        return true;
    }

    public static String getMapperDataType(String dataType){
        if( "text".equalsIgnoreCase(dataType) ){
            return "VARCHAR";
        }
        if( "datetime".equalsIgnoreCase(dataType) ){
            return "TIMESTAMP";
        }
        return dataType.toUpperCase();
    }

    public static String getJavaMethod(String columnName){
        String[] columnNameArr = columnName.split("_");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("get");
        for(int i=0;i<columnNameArr.length;i++){
            stringBuffer.append(getName(columnNameArr[i]));
        }
        return stringBuffer.toString();
    }

    public static String setJavaMethod(String columnName){
        String[] columnNameArr = columnName.split("_");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("set");
        for(int i=0;i<columnNameArr.length;i++){
            stringBuffer.append(getName(columnNameArr[i]));
        }
        return stringBuffer.toString();
    }

    public static String getAttributeName(String tableName){
        String className = getDTOName(tableName);
        return className.substring(0,1).toLowerCase()+className.substring(1);
    }

    public static String getTabs(int index){
        if(index==0){
            return "";
        }
        String tabs = "";
        for(int i=0;i<index;i++){
            tabs+="\t";
        }
        return tabs;
    }

    public static String getResponseClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_RESPONSE;
    }
    public static String getResponseName(String tableName){
        return getAttributeName(tableName)+ NameContant.DATABASE_RESPONSE;
    }
    public static String getRequestClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_REQUEST;
    }
    public static String getSearchRequestClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_SEARCH_REQUEST;
    }
    public static String getRequestName(String tableName){
        return getAttributeName(tableName)+ NameContant.DATABASE_REQUEST;
    }
    public static String getApiClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_API;
    }
    public static String getApiName(String tableName){
        return getAttributeName(tableName)+ NameContant.DATABASE_API;
    }
    public static String getApiImplClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_API_IMPL;
    }
    public static String getDataClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_DATA;
    }
    public static String getDataName(String tableName){
        return getAttributeName(tableName)+ NameContant.DATABASE_DATA;
    }
    public static String getServiceClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_SERVICE;
    }
    public static String getServiceName(String tableName){
        return getAttributeName(tableName)+ NameContant.DATABASE_SERVICE;
    }
    public static String getServiceImplClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_SERVICE_IMPL;
    }
    public static String getMapperClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_MAPPER;
    }
    public static String getMapperExClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_MAPPER_EX;
    }
    public static String getMapperExName(String tableName){
        return getAttributeName(tableName)+ NameContant.DATABASE_MAPPER_EX;
    }

    public static String getFeignClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_FEIGN;
    }

    public static String getControllerClass(String tableName){
        return getDTOName(tableName)+ NameContant.DATABASE_CONTROLLER;
    }
}
