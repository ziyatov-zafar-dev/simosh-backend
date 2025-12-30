package com.codebyz.simoshbackend.storage;

import com.codebyz.simoshbackend.config.StorageProperties;
import com.codebyz.simoshbackend.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final StorageProperties storageProperties;

    public FileStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    public String store(MultipartFile file, String subDir) {
        if (file == null || file.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Fayl topilmadi");
        }
        String uploadsDir = storageProperties.getUploadsDir();
        if (!StringUtils.hasText(uploadsDir)) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Uploads katalogi sozlanmagan");
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        String ext = "";
        int dot = original.lastIndexOf('.');
        if (dot >= 0) {
            ext = original.substring(dot);
        }
        String fileName = UUID.randomUUID() + ext;

        Path dirPath = StringUtils.hasText(subDir)
                ? Paths.get(uploadsDir, subDir)
                : Paths.get(uploadsDir);
        Path filePath = dirPath.resolve(fileName);

        try {
            Files.createDirectories(dirPath);
            Files.write(filePath, file.getBytes());
        } catch (IOException ex) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Faylni saqlashda xatolik");
        }

        String relative = "/" + uploadsDir.replace("\\", "/");
        if (StringUtils.hasText(subDir)) {
            relative = relative + "/" + subDir;
        }
        return relative + "/" + fileName;
    }
}
