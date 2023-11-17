package com.capitole.inditex.v1.service;

import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceService {

    List<Price> retrievePricesByBrandIdAndProductId(Long brandId, Long productId);

    ProductItem retrieveProductWithPriority(LocalDateTime requestDate, Price price);

    ProductItem toApiProductItem(ProductRetrievalRequest retrievalRequest, List<ProductItem> items, ProductItem productItem);

}
