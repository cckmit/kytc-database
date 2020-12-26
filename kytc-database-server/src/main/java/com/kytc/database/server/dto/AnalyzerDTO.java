package com.kytc.database.server.dto;

import com.kytc.database.response.ColumnResponse;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class AnalyzerDTO implements Serializable {
    private String pkg;
    private String tableName;
    private List<ColumnResponse> columnResponses;
    private Map<Boolean, Map<String, java.util.List<ColumnIndexDTO>>> columnMap;
    private String description;
}
