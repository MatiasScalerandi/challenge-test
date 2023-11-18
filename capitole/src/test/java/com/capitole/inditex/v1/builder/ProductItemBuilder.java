package com.capitole.inditex.v1.builder;

import com.capitole.inditex.v1.model.ProductItem;

import java.util.Date;

/**
 * Builder class for {@link ProductItem}
 *
 * @author Matias Scalerandi
 */
public class ProductItemBuilder {
    private ProductItem instance;

    public ProductItemBuilder() {
        this.instance = new ProductItem();
    }

    public ProductItemBuilder withBrandId(final Long brandId) {
        instance.setBrandId(brandId);
        return this;
    }

    public ProductItemBuilder withProductId(final Long productId) {
        instance.setProductId(productId);
        return this;
    }

    public ProductItemBuilder withStartDate(final String startDate) {
        instance.setApplicationStartDate(startDate);
        return this;
    }

    public ProductItemBuilder withEndDate(final Date endDate) {
        instance.setApplicationEndDate(endDate);
        return this;
    }

    public ProductItemBuilder withRateToApply(final Double rate) {
        instance.setRateToApply(rate);
        return this;
    }

    public ProductItemBuilder withFinalPrice(final Double finalPrice) {
        instance.setFinalPrice(finalPrice);
        return this;
    }

    public ProductItemBuilder mockWithBasicData(final String start, final Date end) {
        return new ProductItemBuilder()
                .withBrandId(1L)
                .withProductId(35455L)
                .withStartDate(start)
                .withEndDate(end);
    }

    public ProductItem build() {
        return instance;
    }
}
