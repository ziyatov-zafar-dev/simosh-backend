package com.codebyz.simoshbackend.statistics.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SaleEventResponse {

    private UUID id;
    private java.util.List<OrderItemResponse> items;
    private String status;
    private String statusDescription;
    private String firstName;
    private String lastName;
    private String phone;
    private String description;
    private LocalDateTime createdAt;

    public SaleEventResponse(UUID id,
                             java.util.List<OrderItemResponse> items,
                             String status,
                             String statusDescription,
                             String firstName,
                             String lastName,
                             String phone,
                             String description,
                             LocalDateTime createdAt) {
        this.id = id;
        this.items = items;
        this.status = status;
        this.statusDescription = statusDescription;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.description = description;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public java.util.List<OrderItemResponse> getItems() {
        return items;
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

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
