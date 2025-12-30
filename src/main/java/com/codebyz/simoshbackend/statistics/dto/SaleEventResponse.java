package com.codebyz.simoshbackend.statistics.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SaleEventResponse {

    private UUID id;
    private UUID productId;
    private long quantity;
    private String status;
    private String statusDescription;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime createdAt;

    public SaleEventResponse(UUID id,
                             UUID productId,
                             long quantity,
                             String status,
                             String statusDescription,
                             String firstName,
                             String lastName,
                             String phone,
                             LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.statusDescription = statusDescription;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
