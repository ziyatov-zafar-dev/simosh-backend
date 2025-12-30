package com.codebyz.simoshbackend.statistics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductSalesRepository extends JpaRepository<ProductSales, UUID> {
}
