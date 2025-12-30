package com.codebyz.simoshbackend.statistics.dto;

public enum SaleStatus {
    SOLD("Sotib olinganlar"),
    CANCELED("Sotib olish bekor qilinganlar"),
    IN_PROGRESS("Jarayondagi buyurtmalar");

    private final String description;

    SaleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
