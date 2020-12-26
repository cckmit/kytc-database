package com.kytc.database.server.service.ayalyzer;

import com.kytc.database.server.dto.AnalyzerDTO;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

/**
 * <a style="display:none">简单描述</a>.
 * <a style="display:none">详细描述</a><p></p>
 * <p><strong>目的:</strong></p>
 * <p><strong>原因:</strong></p>
 * <p><strong>用途:</strong></p>
 *
 * @author: 何志同
 * @date: 2019-08-24 21:56
 * @see <a target="_blank" href="">参考文档</a>
 **/
public interface Analyzer {
    List<String> analyzer(AnalyzerDTO analyzerDTO);

    String getFileName(String tableName);

    String getPackage();

    default String getFilePath(String pkg,String tableName){
        if(StringUtils.isEmpty(pkg)){
            return "";
        }
        return File.separator+pkg.replaceAll("\\.",File.separator+File.separator);
    }
}
