package com.capitole.inditex.v1.builder;

import com.capitole.inditex.v1.model.ProductRetrievalRequest;

import java.util.Date;

/**
 * Builder class for {@link ProductRetrievalRequest}
 *
 * @author Matias Scalerandi
 */
public class ProductRetrievalRequestBuilder {

    private ProductRetrievalRequest instance;

    public ProductRetrievalRequestBuilder() {
        this.instance = new ProductRetrievalRequest();
    }

    public ProductRetrievalRequestBuilder withBrandId(final Long brandId) {
        instance.setBrandId(brandId);
        return this;
    }

    public ProductRetrievalRequestBuilder withProductId(final Long productId) {
        instance.setProductId(productId);
        return this;
    }

    public ProductRetrievalRequestBuilder withApplicationStartDate(final String date) {
        instance.setApplicationStartDate(date);
        return this;
    }

    public ProductRetrievalRequestBuilder mockWithBrandIdAndProductId(final String date) {
        return new ProductRetrievalRequestBuilder()
                .withBrandId(1L)
                .withProductId(35455L)
                .withApplicationStartDate(date);
    }

    public ProductRetrievalRequest build() {
        return instance;
    }
}
