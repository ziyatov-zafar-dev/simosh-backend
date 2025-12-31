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
        String forwardedProto = firstHeaderValue(req.getHeader("X-Forwarded-Proto"));
        String forwardedHost = firstHeaderValue(req.getHeader("X-Forwarded-Host"));
        String forwardedPort = firstHeaderValue(req.getHeader("X-Forwarded-Port"));

        String scheme = StringUtils.hasText(forwardedProto) ? forwardedProto : req.getScheme();
        String serverName = StringUtils.hasText(forwardedHost) ? forwardedHost : req.getServerName();
        int serverPort = req.getServerPort();
        if (StringUtils.hasText(forwardedPort)) {
            try {
                serverPort = Integer.parseInt(forwardedPort);
            } catch (NumberFormatException ignored) {
                // Keep server port from the request when forwarded port is invalid.
            }
        }

        boolean isDefaultPort =
                ("http".equals(scheme) && serverPort == 80) ||
                        ("https".equals(scheme) && serverPort == 443);

        if (serverName.contains(":")) {
            serverName = stripDefaultPort(serverName, scheme);
        }
        boolean hostHasPort = serverName.contains(":");
        if (isDefaultPort || hostHasPort) {
            return scheme + "://" + serverName;
        }

        return scheme + "://" + serverName + ":" + serverPort;
    }

    private String firstHeaderValue(String header) {
        if (!StringUtils.hasText(header)) {
            return null;
        }
        int comma = header.indexOf(',');
        return (comma >= 0 ? header.substring(0, comma) : header).trim();
    }

    private String stripDefaultPort(String host, String scheme) {
        String lower = host.toLowerCase();
        if ("https".equals(scheme) && lower.endsWith(":443")) {
            return host.substring(0, host.length() - 4);
        }
        if ("http".equals(scheme) && lower.endsWith(":80")) {
            return host.substring(0, host.length() - 3);
        }
        return host;
    }
}
