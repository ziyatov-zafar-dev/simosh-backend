package com.codebyz.simoshbackend.product.dto;


import java.math.BigDecimal;

public class ProductCreateRequest {

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

    public String getNameUz() {
        return nameUz;
    }

    public void setNameUz(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameTr() {
        return nameTr;
    }

    public void setNameTr(String nameTr) {
        this.nameTr = nameTr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescUz() {
        return descUz;
    }

    public void setDescUz(String descUz) {
        this.descUz = descUz;
    }

    public String getDescRu() {
        return descRu;
    }

    public void setDescRu(String descRu) {
        this.descRu = descRu;
    }

    public String getDescTr() {
        return descTr;
    }

    public void setDescTr(String descTr) {
        this.descTr = descTr;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Long getImgSize() {
        return imgSize;
    }

    public void setImgSize(Long imgSize) {
        this.imgSize = imgSize;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
