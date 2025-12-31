package com.codebyz.simoshbackend.storage;

import com.codebyz.simoshbackend.storage.dto.UploadResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file,
                                                 HttpServletRequest request) {
        String relative = fileStorageService.store(file, "products");
        String baseUrl = getHttpUrl(request);
        String url = buildUrl(baseUrl, relative);
        String fileName = extractFileName(relative);

        UploadResponse response = new UploadResponse(
                url,
                fileName,
                StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename()),
                file.getSize(),
                file.getContentType()
        );
        return ResponseEntity.ok(response);
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

    private String extractFileName(String relative) {
        if (!StringUtils.hasText(relative)) {
            return "";
        }
        String normalized = relative.replace("\\", "/");
        int idx = normalized.lastIndexOf('/');
        return idx >= 0 ? normalized.substring(idx + 1) : normalized;
    }

    private String getHttpUrl(HttpServletRequest req) {
        String rawProto = req.getHeader("X-Forwarded-Proto");
        String rawHost = req.getHeader("X-Forwarded-Host");
        String rawPort = req.getHeader("X-Forwarded-Port");

        String scheme = StringUtils.hasText(rawProto) ? rawProto.split(",")[0].trim() : req.getScheme();
        String host = StringUtils.hasText(rawHost) ? rawHost.split(",")[0].trim() : req.getServerName();
        String portValue = StringUtils.hasText(rawPort) ? rawPort.split(",")[0].trim() : null;

        boolean portExplicit = StringUtils.hasText(portValue);
        int port = req.getServerPort();
        if (portExplicit) {
            try {
                port = Integer.parseInt(portValue);
            } catch (NumberFormatException ignored) {
                portExplicit = false;
            }
        }

        String hostOnly = host;
        int colonIdx = host.indexOf(':');
        if (colonIdx > -1) {
            hostOnly = host.substring(0, colonIdx);
            if (!portExplicit && colonIdx + 1 < host.length()) {
                try {
                    port = Integer.parseInt(host.substring(colonIdx + 1));
                    portExplicit = true;
                } catch (NumberFormatException ignored) {
                    // Ignore invalid host port.
                }
            }
        }

        boolean isIp = hostOnly.matches("\\d{1,3}(\\.\\d{1,3}){3}");
        boolean useHttp = "http".equalsIgnoreCase(scheme) && isIp;
        if (useHttp) {
            return portExplicit ? "http://" + hostOnly + ":" + port : "http://" + hostOnly;
        }

        return "https://" + hostOnly;
    }
}
