package com.codebyz.simoshbackend.about;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "about")
public class About {

    @Id
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String descriptionUz;

    @Column(columnDefinition = "TEXT")
    private String descriptionRu;

    @Column(columnDefinition = "TEXT")
    private String descriptionTr;

    @Column(columnDefinition = "TEXT")
    private String descriptionEn;

    @Column(columnDefinition = "TEXT")
    private String officeAddressUz;

    @Column(columnDefinition = "TEXT")
    private String officeAddressRu;

    @Column(columnDefinition = "TEXT")
    private String officeAddressTr;

    @Column(columnDefinition = "TEXT")
    private String officeAddressEn;

    private String instagram;

    private String telegram;

    private String phone;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptionUz() {
        return descriptionUz;
    }

    public void setDescriptionUz(String descriptionUz) {
        this.descriptionUz = descriptionUz;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDescriptionTr() {
        return descriptionTr;
    }

    public void setDescriptionTr(String descriptionTr) {
        this.descriptionTr = descriptionTr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getOfficeAddressUz() {
        return officeAddressUz;
    }

    public void setOfficeAddressUz(String officeAddressUz) {
        this.officeAddressUz = officeAddressUz;
    }

    public String getOfficeAddressRu() {
        return officeAddressRu;
    }

    public void setOfficeAddressRu(String officeAddressRu) {
        this.officeAddressRu = officeAddressRu;
    }

    public String getOfficeAddressTr() {
        return officeAddressTr;
    }

    public void setOfficeAddressTr(String officeAddressTr) {
        this.officeAddressTr = officeAddressTr;
    }

    public String getOfficeAddressEn() {
        return officeAddressEn;
    }

    public void setOfficeAddressEn(String officeAddressEn) {
        this.officeAddressEn = officeAddressEn;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
