package com.codebyz.simoshbackend.statistics.dto;

import java.util.UUID;

public class ProductSalesResponse {

    private UUID productId;
    private long soldCount;

    public ProductSalesResponse(UUID productId, long soldCount) {
        this.productId = productId;
        this.soldCount = soldCount;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getSoldCount() {
        return soldCount;
    }
}
