package com.codebyz.simoshbackend.statistics;

import com.codebyz.simoshbackend.statistics.dto.ProductSalesResponse;
import com.codebyz.simoshbackend.statistics.dto.SaleEventRequest;
import com.codebyz.simoshbackend.statistics.dto.SaleStatus;
import com.codebyz.simoshbackend.statistics.dto.OrderItemRequest;
import com.codebyz.simoshbackend.statistics.dto.OrderItemResponse;
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
    private final OrderItemRepository orderItemRepository;

    public StatisticsService(StatisticsRepository statisticsRepository,
                             ProductSalesRepository productSalesRepository,
                             SaleEventRepository saleEventRepository,
                             OrderItemRepository orderItemRepository) {
        this.statisticsRepository = statisticsRepository;
        this.productSalesRepository = productSalesRepository;
        this.saleEventRepository = saleEventRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void recordSale(SaleEventRequest request) {
        if (request.getStatus() != SaleStatus.IN_PROGRESS) {
            throw new com.codebyz.simoshbackend.exception.ApiException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Foydalanuvchi faqat IN_PROGRESS status yuborishi mumkin"
            );
        }
        SaleEvent event = new SaleEvent();
        event.setStatus(request.getStatus());
        event.setFirstName(request.getFirstName());
        event.setLastName(request.getLastName());
        event.setPhone(request.getPhone());
        event.setDescription(request.getDescription());
        saleEventRepository.save(event);

        for (OrderItemRequest item : request.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSaleEventId(event.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItemRepository.save(orderItem);
        }
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
                .map(this::toSaleEventResponse)
                .toList();
    }

    public List<SaleEventResponse> getSaleEventsByStatus(SaleStatus status) {
        return saleEventRepository.findAllByStatus(status).stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(this::toSaleEventResponse)
                .toList();
    }

    public SaleEventResponse updateStatus(java.util.UUID id, SaleStatus status) {
        SaleEvent event = saleEventRepository.findById(id)
                .orElseThrow(() -> new com.codebyz.simoshbackend.exception.ApiException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Buyurtma topilmadi"));

        SaleStatus previous = event.getStatus();
        event.setStatus(status);
        saleEventRepository.save(event);

        java.util.List<OrderItem> items = orderItemRepository.findAllBySaleEventId(event.getId());
        long deltaMultiplier = 0;
        if (previous != SaleStatus.SOLD && status == SaleStatus.SOLD) {
            deltaMultiplier = 1;
        } else if (previous == SaleStatus.SOLD && status != SaleStatus.SOLD) {
            deltaMultiplier = -1;
        }
        if (deltaMultiplier != 0) {
            for (OrderItem item : items) {
                adjustCounters(item.getProductId(), deltaMultiplier * item.getQuantity());
            }
        }

        return toSaleEventResponse(event);
    }

    private SaleEventResponse toSaleEventResponse(SaleEvent event) {
        java.util.List<OrderItemResponse> items = orderItemRepository.findAllBySaleEventId(event.getId()).stream()
                .map(item -> new OrderItemResponse(item.getProductId(), item.getQuantity()))
                .toList();
        return new SaleEventResponse(
                event.getId(),
                items,
                event.getStatus().name(),
                event.getStatus().getDescription(),
                event.getFirstName(),
                event.getLastName(),
                event.getPhone(),
                event.getDescription(),
                event.getCreatedAt()
        );
    }

    private void adjustCounters(java.util.UUID productId, long delta) {
        Statistics stats = statisticsRepository.findById(SINGLETON_ID).orElseGet(() -> {
            Statistics created = new Statistics();
            created.setId(SINGLETON_ID);
            created.setTotalSold(0);
            return created;
        });
        stats.setTotalSold(Math.max(0, stats.getTotalSold() + delta));
        statisticsRepository.save(stats);

        ProductSales productSales = productSalesRepository.findById(productId)
                .orElseGet(() -> {
                    ProductSales created = new ProductSales();
                    created.setProductId(productId);
                    created.setSoldCount(0);
                    return created;
                });
        productSales.setSoldCount(Math.max(0, productSales.getSoldCount() + delta));
        productSalesRepository.save(productSales);
    }
}
