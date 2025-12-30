package com.codebyz.simoshbackend.product;

import com.codebyz.simoshbackend.product.dto.ProductCreateRequest;
import com.codebyz.simoshbackend.product.dto.ProductResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductCreateController {

    private final ProductService productService;

    public ProductCreateController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request,
                                                  HttpServletRequest httpRequest) {
        String baseUrl = getHttpUrl(httpRequest);
        return ResponseEntity.ok(productService.create(request, baseUrl));
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
