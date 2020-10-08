package com.kytc.database.server.helper;

import java.util.ArrayList;
import java.util.List;

import com.kytc.database.server.impl.analyzer.*;
import com.kytc.database.server.service.ayalyzer.Analyzer;
import lombok.extern.slf4j.Slf4j;
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
public class AnalyzerHelper {
    private List<Analyzer> list = new ArrayList<>();

    public void putAnalyzer(Analyzer analyzer){
        list.add(analyzer);
    }

    public List<Analyzer> getAnalyzer(){
        return list;
    }
}
