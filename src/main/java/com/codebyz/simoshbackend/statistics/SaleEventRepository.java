package com.codebyz.simoshbackend.statistics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

import com.codebyz.simoshbackend.statistics.dto.SaleStatus;

public interface SaleEventRepository extends JpaRepository<SaleEvent, UUID> {
    List<SaleEvent> findAllByStatus(SaleStatus status);
}
