package com.codebyz.simoshbackend.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductResponse {

    private UUID id;
    private String nameUz;
    private String nameRu;
    private String nameTr;
    private String nameEn;
    private String descUz;
    private String descRu;
    private String descTr;
    private String descEn;
    private BigDecimal price;
    private String imgUrl;
    private String imgName;
    private Long imgSize;
    private String currency;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponse(UUID id,
                           String nameUz,
                           String nameRu,
                           String nameTr,
                           String nameEn,
                           String descUz,
                           String descRu,
                           String descTr,
                           String descEn,
                           BigDecimal price,
                           String imgUrl,
                           String imgName,
                           Long imgSize,
                           String currency,
                           String status,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
        this.id = id;
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameTr = nameTr;
        this.nameEn = nameEn;
        this.descUz = descUz;
        this.descRu = descRu;
        this.descTr = descTr;
        this.descEn = descEn;
        this.price = price;
        this.imgUrl = imgUrl;
        this.imgName = imgName;
        this.imgSize = imgSize;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getNameUz() {
        return nameUz;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getNameTr() {
        return nameTr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getDescUz() {
        return descUz;
    }

    public String getDescRu() {
        return descRu;
    }

    public String getDescTr() {
        return descTr;
    }

    public String getDescEn() {
        return descEn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getImgName() {
        return imgName;
    }

    public Long getImgSize() {
        return imgSize;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
