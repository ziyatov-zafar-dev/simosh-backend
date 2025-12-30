package com.codebyz.simoshbackend.statistics.dto;

public class SaleStatusResponse {

    private String code;
    private String description;

    public SaleStatusResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
