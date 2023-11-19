package com.capitole.inditex.v1.service.impl;

import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.service.PriceService;
import com.capitole.inditex.v1.service.component.PriceComponent;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link PriceService}
 *
 * @author Matias Scalerandi
 */
@Service
public class PriceServiceImpl implements PriceService {

    private final PriceComponent component;

    /**
     * Constructor.
     *
     * @param component {@link PriceComponent}
     */
    public PriceServiceImpl(PriceComponent component) {
        this.component = component;
    }

    /**
     * @see PriceService#retrievePricesByBrandIdAndProductId(Long, Long)
     */
    @Override
    public List<Price> retrievePricesByBrandIdAndProductId(Long brandId, Long productId) {
        Preconditions.checkArgument(Objects.nonNull(brandId), "'brandId' cannot be null");
        Preconditions.checkArgument(Objects.nonNull(productId), "'productId' cannot be null");
        return component.retrieveFinalPriceByBrandIdAndProductId(brandId, productId);
    }

    /**
     * @see PriceService#retrieveProductWithPriority(LocalDateTime, Price)
     */
    @Override
    public Optional<ProductItem> retrieveProductWithPriority(LocalDateTime requestDate, Price price) {
        Preconditions.checkArgument(Objects.nonNull(price), "'price' cannot be null");
        return component.retrievePriceInDateRange(requestDate, price);
    }

    /**
     * @see PriceService#toApiProductItem(ProductRetrievalRequest, List, ProductItem)
     */
    @Override
    public ProductItem toApiProductItem(ProductRetrievalRequest retrievalRequest, List<ProductItem> items, ProductItem productItem) {
        Preconditions.checkArgument(Objects.nonNull(retrievalRequest), "'retrievalRequest' cannot be null");
        Preconditions.checkArgument(Objects.nonNull(productItem), "'productItem' cannot be null");
        return component.buildApiProductItem(retrievalRequest, items, productItem);
    }
}
