package com.capitole.inditex.v1.repository;

import com.capitole.inditex.v1.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for entity {@link Price} based on Spring Data JPA.
 *
 * @author Matias Scalerandi
 */
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    /**
     * Find all Prices filter by brand id and product id
     *
     * @param brandId   the ID of the brand
     * @param productId the ID of the product
     * @return price list {@link List<Price>}
     */
    List<Price> findByBrandIdAndProductId(Long brandId, Long productId);

}
