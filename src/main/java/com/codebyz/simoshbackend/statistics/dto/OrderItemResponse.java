package com.codebyz.simoshbackend.statistics.dto;

import java.util.UUID;

public class OrderItemResponse {

    private UUID productId;
    private long quantity;

    public OrderItemResponse(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
