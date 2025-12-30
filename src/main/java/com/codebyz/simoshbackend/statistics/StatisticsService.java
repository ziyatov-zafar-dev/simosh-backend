package com.codebyz.simoshbackend.statistics;

import com.codebyz.simoshbackend.statistics.dto.ProductSalesResponse;
import com.codebyz.simoshbackend.statistics.dto.SaleEventRequest;
import com.codebyz.simoshbackend.statistics.dto.SaleStatus;
import com.codebyz.simoshbackend.statistics.dto.SaleEventResponse;
import com.codebyz.simoshbackend.statistics.dto.StatisticsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private static final long SINGLETON_ID = 1L;

    private final StatisticsRepository statisticsRepository;
    private final ProductSalesRepository productSalesRepository;
    private final SaleEventRepository saleEventRepository;

    public StatisticsService(StatisticsRepository statisticsRepository,
                             ProductSalesRepository productSalesRepository,
                             SaleEventRepository saleEventRepository) {
        this.statisticsRepository = statisticsRepository;
        this.productSalesRepository = productSalesRepository;
        this.saleEventRepository = saleEventRepository;
    }

    public void recordSale(SaleEventRequest request) {
        SaleEvent event = new SaleEvent();
        event.setProductId(request.getProductId());
        event.setQuantity(request.getQuantity());
        event.setStatus(request.getStatus());
        event.setFirstName(request.getFirstName());
        event.setLastName(request.getLastName());
        event.setPhone(request.getPhone());
        saleEventRepository.save(event);

        if (request.getStatus() != SaleStatus.SOLD) {
            return;
        }
        Statistics stats = statisticsRepository.findById(SINGLETON_ID).orElseGet(() -> {
            Statistics created = new Statistics();
            created.setId(SINGLETON_ID);
            created.setTotalSold(0);
            return created;
        });
        stats.setTotalSold(stats.getTotalSold() + request.getQuantity());
        statisticsRepository.save(stats);

        ProductSales productSales = productSalesRepository.findById(request.getProductId())
                .orElseGet(() -> {
                    ProductSales created = new ProductSales();
                    created.setProductId(request.getProductId());
                    created.setSoldCount(0);
                    return created;
                });
        productSales.setSoldCount(productSales.getSoldCount() + request.getQuantity());
        productSalesRepository.save(productSales);
    }

    public StatisticsResponse getStatistics() {
        Statistics stats = statisticsRepository.findById(SINGLETON_ID).orElseGet(() -> {
            Statistics created = new Statistics();
            created.setId(SINGLETON_ID);
            created.setTotalSold(0);
            return statisticsRepository.save(created);
        });

        List<ProductSalesResponse> products = productSalesRepository.findAll().stream()
                .map(ps -> new ProductSalesResponse(ps.getProductId(), ps.getSoldCount()))
                .toList();

        return new StatisticsResponse(stats.getTotalSold(), products);
    }

    public List<SaleEventResponse> getSaleEvents() {
        return saleEventRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(event -> new SaleEventResponse(
                        event.getId(),
                        event.getProductId(),
                        event.getQuantity(),
                        event.getStatus().name(),
                        event.getStatus().getDescription(),
                        event.getFirstName(),
                        event.getLastName(),
                        event.getPhone(),
                        event.getCreatedAt()
                ))
                .toList();
    }

    public List<SaleEventResponse> getSaleEventsByStatus(SaleStatus status) {
        return saleEventRepository.findAllByStatus(status).stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(event -> new SaleEventResponse(
                        event.getId(),
                        event.getProductId(),
                        event.getQuantity(),
                        event.getStatus().name(),
                        event.getStatus().getDescription(),
                        event.getFirstName(),
                        event.getLastName(),
                        event.getPhone(),
                        event.getCreatedAt()
                ))
                .toList();
    }

    public SaleEventResponse updateStatus(java.util.UUID id, SaleStatus status) {
        SaleEvent event = saleEventRepository.findById(id)
                .orElseThrow(() -> new com.codebyz.simoshbackend.exception.ApiException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Buyurtma topilmadi"));
        event.setStatus(status);
        saleEventRepository.save(event);

        return new SaleEventResponse(
                event.getId(),
                event.getProductId(),
                event.getQuantity(),
                event.getStatus().name(),
                event.getStatus().getDescription(),
                event.getFirstName(),
                event.getLastName(),
                event.getPhone(),
                event.getCreatedAt()
        );
    }
}
