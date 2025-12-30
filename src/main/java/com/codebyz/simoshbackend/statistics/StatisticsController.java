package com.codebyz.simoshbackend.statistics;

import com.codebyz.simoshbackend.auth.dto.MessageResponse;
import com.codebyz.simoshbackend.statistics.dto.SaleEventRequest;
import com.codebyz.simoshbackend.statistics.dto.SaleEventResponse;
import com.codebyz.simoshbackend.statistics.dto.SaleStatus;
import com.codebyz.simoshbackend.statistics.dto.SaleStatusResponse;
import com.codebyz.simoshbackend.statistics.dto.StatisticsResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/sale")
    public ResponseEntity<MessageResponse> recordSale(@Valid @RequestBody SaleEventRequest request) {
        statisticsService.recordSale(request);
        return ResponseEntity.ok(new MessageResponse("Sotuv qayd etildi"));
    }

    @GetMapping
    public ResponseEntity<StatisticsResponse> getStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/statuses")
    public ResponseEntity<java.util.List<SaleStatusResponse>> getStatuses() {
        java.util.List<SaleStatusResponse> statuses = java.util.Arrays.stream(
                com.codebyz.simoshbackend.statistics.dto.SaleStatus.values()
        ).map(status -> new SaleStatusResponse(status.name(), status.getDescription()))
         .toList();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/orders")
    public ResponseEntity<java.util.List<SaleEventResponse>> getOrders() {
        return ResponseEntity.ok(statisticsService.getSaleEvents());
    }

    @GetMapping("/orders/status/{status}")
    public ResponseEntity<java.util.List<SaleEventResponse>> getOrdersByStatus(@PathVariable SaleStatus status) {
        return ResponseEntity.ok(statisticsService.getSaleEventsByStatus(status));
    }

    @PostMapping("/orders/{id}/status/{status}")
    public ResponseEntity<SaleEventResponse> updateOrderStatus(@PathVariable java.util.UUID id,
                                                               @PathVariable SaleStatus status) {
        return ResponseEntity.ok(statisticsService.updateStatus(id, status));
    }
}
