package com.codebyz.simoshbackend.product;

import com.codebyz.simoshbackend.auth.dto.MessageResponse;
import com.codebyz.simoshbackend.product.dto.ProductResponse;
import com.codebyz.simoshbackend.product.dto.ProductUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductResponse>> getActive() {
        return ResponseEntity.ok(productService.getActive());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable UUID id,
                                                  @Valid @RequestBody ProductUpdateRequest request,
                                                  HttpServletRequest httpRequest) {
        String baseUrl = getHttpUrl(httpRequest);
        return ResponseEntity.ok(productService.update(id, request, baseUrl));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Mahsulot muvaffaqiyatli o'chirildi"));
    }

    private final Pattern IP_PATTERN = Pattern.compile(
            "^\\d{1,3}(\\.\\d{1,3}){3}$"
    );

    private boolean isIpAddress(String host) {
        return IP_PATTERN.matcher(host).matches()
                || "localhost".equalsIgnoreCase(host);
    }

    //    private String getHttpUrl(HttpServletRequest req) {
//        String scheme = req.getScheme();
//        String serverName = req.getServerName();
//        int serverPort = req.getServerPort();
//
//        boolean isDefaultPort = ("http".equals(scheme) && serverPort == 80) || ("https".equals(scheme) && serverPort == 443);
//
//        if (isDefaultPort) {
//            return scheme + "://" + serverName;
//        }
//
//        String res = scheme + "://" + serverName + ":" + serverPort;
//        if (res.startsWith("https")) {
//            return res.substring(0, res.length() - 3);
//        }
//        return res;
//    }
    public String getHttpUrl(HttpServletRequest req) {
        String scheme = req.getScheme();          // http / https
        String serverName = req.getServerName();  // domain yoki ip

        // Agar IP bo‘lsa → qanday kelsa shunday qaytar
        if (isIpAddress(serverName)) {
            return scheme + "://" + serverName;
        }

        // Agar DOMAIN bo‘lsa → har doim HTTPS
        String res = "https://" + serverName;
        if (res.endsWith(":80")) {
            return res.substring(0, res.length() - 3);
        }
        return res;
    }
}
