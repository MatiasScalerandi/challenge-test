package com.capitole.inditex.v1.builder;

import com.capitole.inditex.v1.model.ProductRetrievalResponse;

import java.util.Date;

/**
 * Builder class for {@link ProductRetrievalResponse}
 *
 * @author Matias Scalerandi
 */
public class ProductRetrievalResponseBuilder {
    private ProductRetrievalResponse instance;

    public ProductRetrievalResponseBuilder() {
        this.instance = new ProductRetrievalResponse();
    }

    public ProductRetrievalResponseBuilder withBrandId(final Long brandId) {
        instance.setBrandId(brandId);
        return this;
    }

    public ProductRetrievalResponseBuilder withProductId(final Long productId) {
        instance.setProductId(productId);
        return this;
    }

    public ProductRetrievalResponseBuilder withStartDate(final String startDate) {
        instance.setApplicationStartDate(startDate);
        return this;
    }

    public ProductRetrievalResponseBuilder withEndDate(final Date endDate) {
        instance.setApplicationEndDate(endDate);
        return this;
    }

    public ProductRetrievalResponseBuilder withRateToApply(final Double rate) {
        instance.setRateToApply(rate);
        return this;
    }

    public ProductRetrievalResponseBuilder withFinalPrice(final Double finalPrice) {
        instance.setFinalPrice(finalPrice);
        return this;
    }

    public ProductRetrievalResponseBuilder mockWithBasicData(final String start, final Date end) {
        return new ProductRetrievalResponseBuilder()
                .withBrandId(1L)
                .withProductId(35455L)
                .withStartDate(start)
                .withEndDate(end);
    }

    public ProductRetrievalResponse build() {
        return instance;
    }
}
