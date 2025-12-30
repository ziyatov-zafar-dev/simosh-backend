package com.codebyz.simoshbackend.about.dto;

public class AboutResponse {

    private final String descriptionUz;
    private final String descriptionRu;
    private final String descriptionTr;
    private final String descriptionEn;
    private final String officeAddressUz;
    private final String officeAddressRu;
    private final String officeAddressTr;
    private final String officeAddressEn;
    private final String instagram;
    private final String telegram;
    private final String phone;

    public AboutResponse(String descriptionUz,
                         String descriptionRu,
                         String descriptionTr,
                         String descriptionEn,
                         String officeAddressUz,
                         String officeAddressRu,
                         String officeAddressTr,
                         String officeAddressEn,
                         String instagram,
                         String telegram,
                         String phone) {
        this.descriptionUz = descriptionUz;
        this.descriptionRu = descriptionRu;
        this.descriptionTr = descriptionTr;
        this.descriptionEn = descriptionEn;
        this.officeAddressUz = officeAddressUz;
        this.officeAddressRu = officeAddressRu;
        this.officeAddressTr = officeAddressTr;
        this.officeAddressEn = officeAddressEn;
        this.instagram = instagram;
        this.telegram = telegram;
        this.phone = phone;
    }

    public String getDescriptionUz() {
        return descriptionUz;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public String getDescriptionTr() {
        return descriptionTr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getOfficeAddressUz() {
        return officeAddressUz;
    }

    public String getOfficeAddressRu() {
        return officeAddressRu;
    }

    public String getOfficeAddressTr() {
        return officeAddressTr;
    }

    public String getOfficeAddressEn() {
        return officeAddressEn;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getTelegram() {
        return telegram;
    }

    public String getPhone() {
        return phone;
    }
}
