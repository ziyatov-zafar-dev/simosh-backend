package com.codebyz.simoshbackend.statistics.dto;

import java.util.List;

public class StatisticsResponse {

    private long totalSold;
    private List<ProductSalesResponse> products;

    public StatisticsResponse(long totalSold, List<ProductSalesResponse> products) {
        this.totalSold = totalSold;
        this.products = products;
    }

    public long getTotalSold() {
        return totalSold;
    }

    public List<ProductSalesResponse> getProducts() {
        return products;
    }
}
