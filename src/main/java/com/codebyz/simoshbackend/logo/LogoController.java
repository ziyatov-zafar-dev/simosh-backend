package com.codebyz.simoshbackend.logo;

import com.codebyz.simoshbackend.logo.dto.LogoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
