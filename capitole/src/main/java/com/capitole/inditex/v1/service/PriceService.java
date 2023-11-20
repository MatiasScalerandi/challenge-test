package com.capitole.inditex.v1.service;

import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface wich defines operations for {@link Price}
 *
 * @author Matias Scalerandi
 */
public interface PriceService {
    /**
     * Retrieves all {@link Price} by brand id and product id
     *
     * @param brandId   brand id
     * @param productId product id
     * @return List of {@link Price}
     */

    List<Price> retrievePricesByBrandIdAndProductId(Long brandId, Long productId);

    /**
     * Retrieve the {@link Optional<ProductItem>} with priority rate to apply
     *
     * @return Retrieve the {@link Optional<ProductItem>} price in date range
     */
    Optional<ProductItem> retrieveProductWithPriority(LocalDateTime requestDate, Price price);

    /**
     * Retrieve the {@link ProductItem} with the max rate to apply
     * and build the product response with given data
     *
     * @return Retrieve the {@link ProductItem} with max rate
     */
    ProductItem toApiProductItem(ProductRetrievalRequest retrievalRequest, List<ProductItem> items);

}
