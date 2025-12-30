package com.codebyz.simoshbackend.logo.dto;

public class LogoResponse {

    private final String imgUrl;
    private final String imgName;
    private final Long imgSize;

    public LogoResponse(String imgUrl, String imgName, Long imgSize) {
        this.imgUrl = imgUrl;
        this.imgName = imgName;
        this.imgSize = imgSize;
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
}
