package com.capitole.inditex.v1.repository;

import com.capitole.inditex.v1.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByBrandIdAndProductId(Long brandId, Long productId);

    List<Price> findAllByBrandId(Long brandId);
}
