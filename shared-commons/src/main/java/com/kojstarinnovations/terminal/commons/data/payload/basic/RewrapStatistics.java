package com.kojstarinnovations.terminal.commons.data.payload.basic;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RewrapStatistics {
    private final Map<String, RewrapEntityResult> results = new HashMap<>();
    private final List<String> errors = new ArrayList<>();
    @Setter
    private long durationMs;
    @Setter
    private String fatalError;

    public void addEntityResult(String entityName, RewrapEntityResult result) {
        results.put(entityName, result);
    }

    public void addError(String entityName, String error) {
        errors.add(entityName + ": " + error);
    }

    public int getTotalRecords() {
        return results.values().stream()
                .mapToInt(RewrapEntityResult::getTotalRecords)
                .sum();
    }

    public int getTotalRewrapped() {
        return results.values().stream()
                .mapToInt(RewrapEntityResult::getRewrappedRecords)
                .sum();
    }

    public int getTotalFieldsRewrapped() {
        return results.values().stream()
                .mapToInt(RewrapEntityResult::getFieldsRewrapped)
                .sum();
    }

    public int getTotalErrors() {
        return results.values().stream()
                .mapToInt(RewrapEntityResult::getErrors)
                .sum() + errors.size();
    }

    public boolean hasErrors() {
        return fatalError != null || !errors.isEmpty() || getTotalErrors() > 0;
    }

    @Override
    public String toString() {
        return String.format(
                "RewrapStatistics{entities=%d, totalRecords=%d, rewrapped=%d, fieldsRewrapped=%d, errors=%d, duration=%dms}",
                results.size(),
                getTotalRecords(),
                getTotalRewrapped(),
                getTotalFieldsRewrapped(),
                getTotalErrors(),
                durationMs
        );
    }
}