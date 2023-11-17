package com.capitole.inditex.v1.adapter;

import com.capitole.inditex.v1.mapper.ProductMapper;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.model.ProductRetrievalResponse;
import org.springframework.stereotype.Component;

@Component
public class PriceAdapter {

    private final ProductMapper mapper;

    public PriceAdapter(ProductMapper mapper) {
        this.mapper = mapper;
    }

    public ProductRetrievalResponse toApiProductRetrievalResponse(ProductItem item) {
        return new ProductRetrievalResponse()
                .withProductId(item.getProductId())
                .withBrandId(item.getBrandId())
                .withApplicationStartDate(item.getApplicationStartDate())
                .withApplicationEndDate(item.getApplicationEndDate())
                .withFinalPrice(item.getFinalPrice())
                .withRateToApply(item.getRateToApply());
    }

    public ProductItem requestToProductItem(ProductRetrievalRequest request) {
        return mapper.requestToProductItem(request)
                .withRateToApply(request.getFinalPrice());
    }
}
