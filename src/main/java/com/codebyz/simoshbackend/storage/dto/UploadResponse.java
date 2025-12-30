package com.codebyz.simoshbackend.storage.dto;

public class UploadResponse {

    private final String url;
    private final String fileName;
    private final String originalName;
    private final long fileSize;
    private final String contentType;

    public UploadResponse(String url, String fileName, String originalName, long fileSize, String contentType) {
        this.url = url;
        this.fileName = fileName;
        this.originalName = originalName;
        this.fileSize = fileSize;
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }
}
