package com.codebyz.simoshbackend.product;

import com.codebyz.simoshbackend.exception.ApiException;
import com.codebyz.simoshbackend.product.dto.ProductCreateRequest;
import com.codebyz.simoshbackend.product.dto.ProductResponse;
import com.codebyz.simoshbackend.product.dto.ProductUpdateRequest;
import com.codebyz.simoshbackend.storage.FileStorageService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository, FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.fileStorageService = fileStorageService;
    }

    public ProductResponse create(ProductCreateRequest request, String baseUrl) {
        Product product = new Product();
        applyCreate(product, request, baseUrl);
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductResponse> getActive() {
        return productRepository.findAllByStatusIgnoreCase("ACTIVE").stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(this::toResponse)
                .toList();
    }
    public ProductResponse getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Mahsulot topilmadi"));
        return toResponse(product);
    }

    public ProductResponse update(UUID id, ProductUpdateRequest request, String baseUrl) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Mahsulot topilmadi"));

        applyUpdate(product, request, baseUrl);
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    private void applyUpdate(Product product, ProductUpdateRequest request, String baseUrl) {
        boolean changed = false;

        if (StringUtils.hasText(request.getNameUz())) {
            product.setNameUz(request.getNameUz());
            changed = true;
        }
        if (StringUtils.hasText(request.getNameRu())) {
            product.setNameRu(request.getNameRu());
            changed = true;
        }
        if (StringUtils.hasText(request.getNameTr())) {
            product.setNameTr(request.getNameTr());
            changed = true;
        }
        if (StringUtils.hasText(request.getNameEn())) {
            product.setNameEn(request.getNameEn());
            changed = true;
        }
        if (StringUtils.hasText(request.getDescUz())) {
            product.setDescUz(request.getDescUz());
            changed = true;
        }
        if (StringUtils.hasText(request.getDescRu())) {
            product.setDescRu(request.getDescRu());
            changed = true;
        }
        if (StringUtils.hasText(request.getDescTr())) {
            product.setDescTr(request.getDescTr());
            changed = true;
        }
        if (StringUtils.hasText(request.getDescEn())) {
            product.setDescEn(request.getDescEn());
            changed = true;
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
            changed = true;
        }
        if (StringUtils.hasText(request.getImgUrl())) {
            product.setImgUrl(normalizeImageUrl(request.getImgUrl(), baseUrl));
            changed = true;
        }
        if (StringUtils.hasText(request.getImgName())) {
            product.setImgName(request.getImgName());
            changed = true;
        }
        if (request.getImgSize() != null) {
            product.setImgSize(request.getImgSize());
            changed = true;
        }
        if (StringUtils.hasText(request.getCurrency())) {
            product.setCurrency(request.getCurrency());
            changed = true;
        }
        if (StringUtils.hasText(request.getStatus())) {
            product.setStatus(request.getStatus());
            changed = true;
        }

        if (!changed) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Yangilash uchun kamida bitta maydon yuboring");
        }
    }

    public void delete(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Mahsulot topilmadi"));
        productRepository.delete(product);
    }

    private void applyCreate(Product product,
                             ProductCreateRequest request,
                             String baseUrl) {
        product.setNameUz(request.getNameUz());
        product.setNameRu(request.getNameRu());
        product.setNameTr(request.getNameTr());
        product.setNameEn(request.getNameEn());
        product.setDescUz(request.getDescUz());
        product.setDescRu(request.getDescRu());
        product.setDescTr(request.getDescTr());
        product.setDescEn(request.getDescEn());
        product.setPrice(request.getPrice());
        if (!StringUtils.hasText(request.getImgUrl())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "imgUrl yuborilishi shart");
        }
        product.setImgUrl(normalizeImageUrl(request.getImgUrl(), baseUrl));
        product.setImgName(request.getImgName());
        product.setImgSize(request.getImgSize());
        product.setCurrency(request.getCurrency());
        product.setStatus(request.getStatus());
    }

    private String normalizeImageUrl(String imgUrl, String baseUrl) {
        if (!StringUtils.hasText(imgUrl)) {
            return imgUrl;
        }
        String lower = imgUrl.toLowerCase();
        if (lower.startsWith("http://") || lower.startsWith("https://")) {
            return imgUrl;
        }
        if (!StringUtils.hasText(baseUrl)) {
            return imgUrl;
        }
        String trimmedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        if (imgUrl.startsWith("/")) {
            return trimmedBase + imgUrl;
        }
        return trimmedBase + "/" + imgUrl;
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getNameUz(),
                product.getNameRu(),
                product.getNameTr(),
                product.getNameEn(),
                product.getDescUz(),
                product.getDescRu(),
                product.getDescTr(),
                product.getDescEn(),
                product.getPrice(),
                product.getImgUrl(),
                product.getImgName(),
                product.getImgSize(),
                product.getCurrency(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
