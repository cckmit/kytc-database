package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * @date: 2019-08-24 22:39
 * @see <a target="_blank" href="">参考文档</a>
 **/
@Component
@Slf4j
public class ServiceImplAnalyzer implements Analyzer{
    @Autowired
    public ServiceImplAnalyzer(AnalyzerHelper analyzerHelper){
        analyzerHelper.putAnalyzer(this);
    }
    @Override
    public List<String> analyzer(String pkg, String tableName, List<ColumnResponse> columnResponses,String description) {
        String responseClass = DatabaseUtils.getResponseClass(tableName);
        String requestClass = DatabaseUtils.getRequestClass(tableName);
        String dataClass = DatabaseUtils.getDataClass(tableName);
        String dataName = DatabaseUtils.getDataName(tableName);
        String mapperExName = DatabaseUtils.getMapperExName(tableName);
        ColumnResponse priColumn = DatabaseUtils.getPriColumn(columnResponses);
        List<String> list = new ArrayList<>();
        list.add("package "+pkg+".server.impl;\n");
        list.add("import com.kytc.framework.exception.BaseErrorCodeEnum;\n" +
                "import com.kytc.framework.exception.BaseException;");
        list.add("import com.kytc.framework.web.common.BasePageResponse;");
        list.add("import com.kytc.framework.web.utils.BeanUtils;");
        list.add("import "+pkg+".server.service."+DatabaseUtils.getServiceClass(tableName)+";");
        list.add("import "+pkg+".request."+DatabaseUtils.getRequestClass(tableName)+";");
        list.add("import "+pkg+".request."+DatabaseUtils.getSearchRequestClass(tableName)+";");
        list.add("import "+pkg+".response."+DatabaseUtils.getResponseClass(tableName)+";");
        list.add("import "+pkg+".dao.data."+DatabaseUtils.getDataClass(tableName)+";");
        list.add("import "+pkg+".dao.mapper."+DatabaseUtils.getMapperExClass(tableName)+";");
        list.add("import lombok.RequiredArgsConstructor;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n"+
                "import org.springframework.stereotype.Component;\n");
        list.add("import java.util.Date;");
        list.add("import java.util.List;\n");
        list.add("\n@Component");
        list.add("@RequiredArgsConstructor(onConstructor_={@Autowired})");
        list.add("public class "+DatabaseUtils.getServiceImplClass(tableName)+" implements "+DatabaseUtils.getServiceClass(tableName)+" {");
        list.add("\tprivate final "+DatabaseUtils.getMapperExClass(tableName)+" "+DatabaseUtils.getMapperExName(tableName)+";");
        list.add("\n\t@Override");
        list.add("\tpublic Long add("+requestClass+" request){");
        list.add("\t\t"+dataClass+" "+dataName+" = BeanUtils.convert(request, "+dataClass+".class);");
        list.add("\t\t"+dataName+".setCreatedAt(new Date());");
        list.add("\t\t"+dataName+".setUpdatedAt(new Date());");
        list.add("\t\tthis."+mapperExName+".insert("+dataName+");");
        list.add("\t\tif(null == "+dataName+"."+DatabaseUtils.getJavaMethod(priColumn.getColumnName())+"()){");
        list.add("\t\t\tthrow new BaseErrorCodeEnum.SYSTEM_ERROR,\"添加失败\");");
        list.add("\t\t}");
        list.add("\t\treturn "+dataName+"."+DatabaseUtils.getJavaMethod(priColumn.getColumnName())+"();");
        list.add("\t}");
        list.add("\n\t@Override");
        list.add("\tpublic boolean update("+requestClass+" request){");
        list.add("\t\tif( null != request ){");
        list.add("\t\t\t"+dataClass+" "+dataName+" = BeanUtils.convert(request, "+dataClass+".class);");
        list.add("\t\t\t"+dataName+".setUpdatedAt(new Date());");
        list.add("\t\t\treturn this."+mapperExName+".updateByPrimaryKey("+dataName+")>0;");
        list.add("\t\t}");
        list.add("\t\treturn false;");
        list.add("\t}");
        list.add("\n\t@Override");
        list.add("\tpublic "+responseClass+" detail(Long id){");
        list.add("\t\t"+dataClass+" "+dataName+" = this."+mapperExName+".selectByPrimaryKey( id );");
        list.add("\t\tif( null == "+dataName+" ){");
        list.add("\t\t\treturn null;");
        list.add("\t\t}");
        list.add("\t\treturn BeanUtils.convert("+dataName+","+responseClass+".class);");
        list.add("\t}");
        list.add("\n\t@Override");
        list.add("\tpublic boolean delete(Long id){");
        if(DatabaseUtils.isRealDelete(columnResponses)){
            list.add("\t\treturn this."+mapperExName+".deleteByPrimaryKey(id)>0;");
        }else{
            list.add("\t\t"+DatabaseUtils.getDataClass(tableName)+" "+DatabaseUtils.getDataName(tableName)+" = new "+DatabaseUtils.getDataClass(tableName)+"();");
            list.add("\t\t"+DatabaseUtils.getDataName(tableName)+"."+DatabaseUtils.setJavaMethod(priColumn.getColumnName())+"(id);");
            list.add("\t\t"+DatabaseUtils.getDataName(tableName)+".setIsDeleted(true);");
            list.add("\t\t"+DatabaseUtils.getDataName(tableName)+".setUpdatedAt(new Date());");
            list.add("\t\treturn "+"this."+mapperExName+".updateByPrimaryKeySelective("+dataName+")>0;");
        }
        list.add("\t}");
        list.add("\n\t@Override");
        list.add("\tpublic BasePageResponse<"+ responseClass +"> listByCondition( "+DatabaseUtils.getSearchRequestClass(tableName)+" request ){");
        String column = "";
        for(ColumnResponse columnResponse:columnResponses){
            String name = DatabaseUtils.getJavaName(columnResponse.getColumnName());
            if(Arrays.asList("createdAt","createdBy","updatedAt","updatedBy","lastUpdatedAt","isDeleted").contains(name)){
                continue;
            }
            column+= "request."+DatabaseUtils.getJavaMethod(columnResponse.getColumnName())+"(), ";
        }
        list.add("\t\tBasePageResponse<"+responseClass+"> pageResponse = new BasePageResponse<>();");
        list.add("\t\tpageResponse.setRows(this.listByConditionData( request ));");
        if(column.endsWith(", ")){
            column = column.substring(0,column.length()-2);
        }
        list.add("\t\tpageResponse.setTotal(this.countByConditionData( request ));");
        list.add("\t\tpageResponse.setPage(request.getPage());");
        list.add("\t\tpageResponse.setPageSize(request.getPageSize());");
        list.add("\t\treturn pageResponse;");
        list.add("\t}");

        list.add("\n\tprivate List<"+responseClass+"> listByConditionData( "+DatabaseUtils.getRequestClass(tableName)+" request ){");
        list.add("\t\trequest.init();");
        list.add("\t\tList<"+dataClass+"> list =  this."+mapperExName+".listByCondition("+column+", request.getStart(), request.getLimit());");
        list.add("\t\treturn BeanUtils.convert(list,"+responseClass+".class);");
        list.add("\t}");
        list.add("\n");
        list.add("\tprivate Long countByConditionData("+DatabaseUtils.getRequestClass(tableName)+" request){" );
        list.add("\t\treturn this."+mapperExName+".countByCondition("+column+");");
        list.add("\t}");


        list.add("}");
        return list;
    }


    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getServiceImplClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "server"+ File.separator+"impl";
    }
}
