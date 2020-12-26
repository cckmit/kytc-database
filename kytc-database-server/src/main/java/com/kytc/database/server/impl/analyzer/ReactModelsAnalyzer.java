package com.kytc.database.server.impl.analyzer;

import java.io.File;
import java.util.ArrayList;
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
public class ReactModelsAnalyzer implements Analyzer{
    @Autowired
    public ReactModelsAnalyzer(AnalyzerHelper analyzerHelper){
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
        String tableName = analyzerDTO.getTableName();
        String loading = getLoading(tableName);
        String dtoName = DatabaseUtils.getDTOName(tableName);
        List<String> list = new ArrayList<>();
        list.add("import {query"+dtoName+"s, add"+dtoName+", edit"+dtoName+", delete"+dtoName+"} from '@/services/"+getLoading(tableName)+"';\n");
        list.add("import {notification} from 'antd';\n");
        list.add("export default {\n" +
                DatabaseUtils.getTabs(1)+"namespace:'"+loading+"',\n" +
                DatabaseUtils.getTabs(1)+"state: {\n" +
                DatabaseUtils.getTabs(2)+"data: {\n" +
                DatabaseUtils.getTabs(3)+"rows:[],\n" +
                DatabaseUtils.getTabs(3)+"'total':100,\n" +
                DatabaseUtils.getTabs(3)+"'pageindex':0,\n" +
                DatabaseUtils.getTabs(3)+"'pagesize':10,\n" +
                DatabaseUtils.getTabs(2)+"}\n" +
                DatabaseUtils.getTabs(1)+"},\n" +
                DatabaseUtils.getTabs(1)+"effects:{\n" +
                DatabaseUtils.getTabs(2)+"*query"+dtoName+"s({payload},{call,put}){\n" +
                DatabaseUtils.getTabs(3)+"const response = yield call(query"+dtoName+"s,payload);\n" +
                DatabaseUtils.getTabs(3)+"if(response.status==null){\n" +
                DatabaseUtils.getTabs(4)+"yield put({\n" +
                DatabaseUtils.getTabs(5)+"type: 'quert"+dtoName+"s',\n" +
                DatabaseUtils.getTabs(5)+"payload: response\n" +
                DatabaseUtils.getTabs(4)+"});\n" +
                DatabaseUtils.getTabs(3)+"}else{\n" +
                DatabaseUtils.getTabs(4)+"notification.error({\n" +
                DatabaseUtils.getTabs(5)+"message:\"查询失败\"\n" +
                DatabaseUtils.getTabs(4)+"})\n" +
                DatabaseUtils.getTabs(3)+"}\n" +
                DatabaseUtils.getTabs(2)+"},\n" +
                DatabaseUtils.getTabs(2)+"*add"+dtoName+"({payload,callback},{call,put}){\n" +
                DatabaseUtils.getTabs(3)+"const response = yield call(add"+dtoName+",payload);\n" +
                DatabaseUtils.getTabs(3)+"if(response.status==null){\n" +
                DatabaseUtils.getTabs(4)+"callback && callback();\n" +
                DatabaseUtils.getTabs(3)+"}else{\n" +
                DatabaseUtils.getTabs(4)+"notification.error({\n" +
                DatabaseUtils.getTabs(5)+"message:\"添加失败\"\n" +
                DatabaseUtils.getTabs(4)+"})\n" +
                DatabaseUtils.getTabs(3)+"}\n" +
                DatabaseUtils.getTabs(2)+"},\n" +
                DatabaseUtils.getTabs(2)+"*edit"+dtoName+"({payload,callback},{call,put}){\n" +
                DatabaseUtils.getTabs(3)+"const response = yield call(edit"+dtoName+",payload);\n" +
                DatabaseUtils.getTabs(3)+"if(response.status==null){\n" +
                DatabaseUtils.getTabs(4)+"callback && callback();\n" +
                DatabaseUtils.getTabs(3)+"}else{\n" +
                DatabaseUtils.getTabs(4)+"notification.error({\n" +
                DatabaseUtils.getTabs(5)+"message:\"修改失败\"\n" +
                DatabaseUtils.getTabs(4)+"})\n" +
                DatabaseUtils.getTabs(3)+"}\n" +
                DatabaseUtils.getTabs(2)+"},\n" +
                DatabaseUtils.getTabs(2)+"*delete"+dtoName+"({payload,callback},{call,put}){\n" +
                DatabaseUtils.getTabs(3)+"const response = yield call(delete"+dtoName+",payload);\n" +
                DatabaseUtils.getTabs(3)+"if(response.status==null){\n" +
                DatabaseUtils.getTabs(4)+"callback && callback();\n" +
                DatabaseUtils.getTabs(3)+"}else{\n" +
                DatabaseUtils.getTabs(4)+"notification.error({\n" +
                DatabaseUtils.getTabs(5)+"message:\"修改失败\"\n" +
                DatabaseUtils.getTabs(4)+"})\n" +
                DatabaseUtils.getTabs(3)+"}\n" +
                DatabaseUtils.getTabs(2)+"}\n" +
                DatabaseUtils.getTabs(1)+"},\n" +
                DatabaseUtils.getTabs(1)+"reducers:{\n" +
                DatabaseUtils.getTabs(2)+"query"+dtoName+"s(state,action){\n" +
                DatabaseUtils.getTabs(3)+"return {\n" +
                DatabaseUtils.getTabs(4)+"...state,\n" +
                DatabaseUtils.getTabs(4)+"data:action.payload\n" +
                DatabaseUtils.getTabs(3)+"}\n" +
                DatabaseUtils.getTabs(2)+"}\n" +
                DatabaseUtils.getTabs(1)+"}\n" +
                "}");
        return list;
    }

    @Override
    public String getFileName(String tableName) {
        return "model.js";
    }

    @Override
    public String getPackage() {
        return "react";
    }
}
