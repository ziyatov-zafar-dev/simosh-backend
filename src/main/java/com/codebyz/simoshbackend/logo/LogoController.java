package com.codebyz.simoshbackend.logo;

import com.codebyz.simoshbackend.logo.dto.LogoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/logo")
public class LogoController {

    private final LogoService logoService;

    public LogoController(LogoService logoService) {
        this.logoService = logoService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LogoResponse> upload(@RequestParam("file") MultipartFile file,
                                               HttpServletRequest request) {
        String baseUrl = getHttpUrl(request);
        return ResponseEntity.ok(logoService.upload(file, baseUrl));
    }

    @GetMapping
    public ResponseEntity<LogoResponse> get() {
        return ResponseEntity.ok(logoService.get());
    }

    private String getHttpUrl(HttpServletRequest req) {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();

        boolean isDefaultPort =
                ("http".equals(scheme) && serverPort == 80) ||
                        ("https".equals(scheme) && serverPort == 443);

        if (isDefaultPort) {
            return scheme + "://" + serverName;
        }

        return scheme + "://" + serverName + ":" + serverPort;
    }
}
