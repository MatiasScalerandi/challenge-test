package com.capitole.inditex.v1.repository;

import com.capitole.inditex.v1.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
}
