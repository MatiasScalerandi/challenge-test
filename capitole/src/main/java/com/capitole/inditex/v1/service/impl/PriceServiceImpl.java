package com.capitole.inditex.v1.service.impl;

import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.service.PriceService;
import com.capitole.inditex.v1.service.component.PriceComponent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceComponent component;

    public PriceServiceImpl(PriceComponent component) {
        this.component = component;
    }

    @Override
    public List<Price> retrievePricesByBrandIdAndProductId(Long brandId, Long productId) {
        return component.retrieveFinalPriceByBrandIdAndProductId(brandId, productId);
    }

    @Override
    public Optional<ProductItem> retrieveProductWithPriority(LocalDateTime requestDate, Price price) {
        return component.retrievePriceInDateRange(requestDate, price);
    }

    @Override
    public ProductItem toApiProductItem(ProductRetrievalRequest retrievalRequest, List<ProductItem> items, ProductItem productItem) {
        return component.buildApiProductItem(retrievalRequest, items, productItem);
    }
}
