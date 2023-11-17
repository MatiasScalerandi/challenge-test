package com.capitole.inditex.v1.service;

import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceService {

    List<Price> retrievePricesByBrandIdAndProductId(Long brandId, Long productId);

    Optional<ProductItem> retrieveProductWithPriority(LocalDateTime requestDate, Price price);

    ProductItem toApiProductItem(ProductRetrievalRequest retrievalRequest, List<ProductItem> items, ProductItem productItem);

}
