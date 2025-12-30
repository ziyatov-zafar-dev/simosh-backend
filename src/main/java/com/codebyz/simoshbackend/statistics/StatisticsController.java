package com.codebyz.simoshbackend.statistics;

import com.codebyz.simoshbackend.auth.dto.MessageResponse;
import com.codebyz.simoshbackend.statistics.dto.SaleEventRequest;
import com.codebyz.simoshbackend.statistics.dto.StatisticsResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
