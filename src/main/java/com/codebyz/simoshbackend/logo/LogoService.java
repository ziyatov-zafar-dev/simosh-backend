package com.codebyz.simoshbackend.logo;

import com.codebyz.simoshbackend.logo.dto.LogoResponse;
import com.codebyz.simoshbackend.storage.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LogoService {

    private static final long SINGLETON_ID = 1L;

    private final LogoRepository logoRepository;
    private final FileStorageService fileStorageService;

    public LogoService(LogoRepository logoRepository, FileStorageService fileStorageService) {
        this.logoRepository = logoRepository;
        this.fileStorageService = fileStorageService;
    }

    public LogoResponse upload(MultipartFile file, String baseUrl) {
        String relative = fileStorageService.store(file, "logos");
        String url = buildUrl(baseUrl, relative);

        Logo logo = logoRepository.findById(SINGLETON_ID).orElseGet(() -> {
            Logo created = new Logo();
            created.setId(SINGLETON_ID);
            return created;
        });

        logo.setImgUrl(url);
        logo.setImgName(StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename()));
        logo.setImgSize(file.getSize());

        Logo saved = logoRepository.save(logo);
        return new LogoResponse(saved.getImgUrl(), saved.getImgName(), saved.getImgSize());
    }

    public LogoResponse get() {
        return logoRepository.findById(SINGLETON_ID)
                .map(logo -> new LogoResponse(logo.getImgUrl(), logo.getImgName(), logo.getImgSize()))
                .orElseGet(() -> {
                    Logo logo = new Logo();
                    logo.setId(SINGLETON_ID);
                    logo.setImgUrl("");
                    logo.setImgName("");
                    logo.setImgSize(0L);
                    Logo saved = logoRepository.save(logo);
                    return new LogoResponse(saved.getImgUrl(), saved.getImgName(), saved.getImgSize());
                });
    }

    private String buildUrl(String baseUrl, String relative) {
        if (!StringUtils.hasText(relative)) {
            return relative;
        }
        String trimmedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        if (relative.startsWith("/")) {
            return trimmedBase + relative;
        }
        return trimmedBase + "/" + relative;
    }
}
