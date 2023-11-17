package com.capitole.inditex.v1.adapter;

import com.capitole.inditex.v1.mapper.ProductMapper;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.model.ProductRetrievalResponse;
import org.springframework.stereotype.Component;

/**
 * Adapter between the internal and API model
 *
 * @author Matias Scalerandi
 */
@Component
public class PriceAdapter {

    private final ProductMapper mapper;

    /**
     * Constructor.
     *
     * @param mapper {@link ProductMapper}
     */
    public PriceAdapter(ProductMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Adapts the given product {@link ProductItem} to {@link ProductRetrievalResponse} API model
     *
     * @param item the product with the to apply
     * @return the {@link ProductRetrievalResponse} API object model
     */
    public ProductRetrievalResponse toApiProductRetrievalResponse(ProductItem item) {
        return new ProductRetrievalResponse()
                .withProductId(item.getProductId())
                .withBrandId(item.getBrandId())
                .withApplicationStartDate(item.getApplicationStartDate())
                .withApplicationEndDate(item.getApplicationEndDate())
                .withFinalPrice(item.getFinalPrice())
                .withRateToApply(item.getRateToApply());
    }

    /**
     * Adapts the given product {@link ProductRetrievalRequest} to {@link ProductItem} API model
     *
     * @param request the price info given from request
     * @return the {@link ProductItem} API object model
     */
    public ProductItem requestToProductItem(ProductRetrievalRequest request) {
        return mapper.requestToProductItem(request)
                .withRateToApply(request.getFinalPrice());
    }
}
