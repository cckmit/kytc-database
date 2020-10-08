package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.config.NameContant;
import com.kytc.database.server.helper.AnalyzerHelper;
import com.kytc.database.server.service.ayalyzer.Analyzer;
import com.kytc.database.server.utils.DatabaseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
public class ApiAnalyzer implements Analyzer{
    @Autowired
    public ApiAnalyzer(AnalyzerHelper analyzerHelper){
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
        list.add("package "+pkg+".api;\n");
        list.add("import "+pkg+".request."+ DatabaseUtils.getRequestClass(tableName)+";");
        list.add("import "+pkg+".response."+ DatabaseUtils.getResponseClass(tableName)+";\n");
        list.add("import com.kytc.framework.web.common.BasePageResponse;");
        list.add("import org.springframework.web.bind.annotation.*;");
        list.add("import io.swagger.annotations.Api;\n" +
                "import io.swagger.annotations.ApiOperation;");
        list.add("\nimport javax.validation.Valid;");
        list.add("\n@Api(tags = \""+description+"操作\")");
        list.add("@RequestMapping(\""+getUrl(tableName)+"\")");
        list.add("public interface "+ DatabaseUtils.getDTOName(tableName)+NameContant.DATABASE_API+" {");
        list.add("\n\t@ApiOperation(\"查询"+description+"列表\")");
        list.add("\t@PostMapping(\"/infos\")");
        list.add("\tBasePageResponse<"+ DatabaseUtils.getResponseClass(tableName)+"> listByCondition(");
        list.add("\t\t@RequestBody "+ DatabaseUtils.getRequestClass(tableName)+" request,");
        list.add("\t\t@RequestParam(\"page\")int page,");
        list.add("\t\t@RequestParam(\"pageSize\")int pageSize);");

        list.add("\n\t@ApiOperation(\"添加"+description+"数据\")");
        list.add("\t@PostMapping(\"/info\")");
        list.add("\tboolean add(@RequestBody @Valid "+ DatabaseUtils.getRequestClass(tableName)+" request);");
        list.add("\n\t@ApiOperation(\"修改"+description+"数据\")");
        list.add("\t@PutMapping(\"/info\")");
        list.add("\tboolean update(@RequestBody @Valid "+ DatabaseUtils.getRequestClass(tableName)+" request);");
        list.add("\n\t@ApiOperation(\"删除"+description+"数据\")");
        list.add("\t@DeleteMapping(\"/{id}\")");
        list.add("\tboolean delete(@PathVariable(\"id\")Long id);");
        list.add("\n\t@ApiOperation(\"查询"+description+"详情\")");
        list.add("\t@GetMapping(\"/{id}\")");
        list.add("\t"+ DatabaseUtils.getResponseClass(tableName)+" detail(@PathVariable(\"id\")Long id);");
        list.add("}");
        return list;
    }
    @Override
    public String getFileName(String tableName) {
        return DatabaseUtils.getApiClass(tableName)+".java";
    }

    @Override
    public String getPackage() {
        return "api";
    }
}
