package com.codebyz.simoshbackend.statistics;

import com.codebyz.simoshbackend.statistics.dto.ProductSalesResponse;
import com.codebyz.simoshbackend.statistics.dto.SaleEventRequest;
import com.codebyz.simoshbackend.statistics.dto.StatisticsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private static final long SINGLETON_ID = 1L;

    private final StatisticsRepository statisticsRepository;
    private final ProductSalesRepository productSalesRepository;

    public StatisticsService(StatisticsRepository statisticsRepository,
                             ProductSalesRepository productSalesRepository) {
        this.statisticsRepository = statisticsRepository;
        this.productSalesRepository = productSalesRepository;
    }

    public void recordSale(SaleEventRequest request) {
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
}
