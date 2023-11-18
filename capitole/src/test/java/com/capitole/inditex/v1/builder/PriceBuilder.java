package com.capitole.inditex.v1.builder;

import com.capitole.inditex.v1.entity.CurrencyIsoType;
import com.capitole.inditex.v1.entity.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Builder class for {@link Price}
 *
 * @author Matias Scalerandi
 */
public class PriceBuilder {
    private Price instance;

    public PriceBuilder() {
        this.instance = new Price();
    }

    public PriceBuilder withId(final Long id) {
        instance.setId(id);
        return this;
    }

    public PriceBuilder withBrandId(final Long brandId) {
        instance.setBrandId(brandId);
        return this;
    }

    public PriceBuilder withProductId(final Long productId) {
        instance.setProductId(productId);
        return this;
    }

    public PriceBuilder withStartDate(final LocalDateTime date) {
        instance.setStartDate(date);
        return this;
    }

    public PriceBuilder withEndDate(final LocalDateTime brandId) {
        instance.setEndDate(brandId);
        return this;
    }

    public PriceBuilder withPriceListId(final Long priceListId) {
        instance.setPriceListId(priceListId);
        return this;
    }

    public PriceBuilder withPriority(final boolean isPriority) {
        instance.setPriority(isPriority);
        return this;
    }

    public PriceBuilder withFinalPrice(final BigDecimal finalPrice) {
        instance.setFinalPrice(finalPrice);
        return this;
    }

    public PriceBuilder withCurrency(final CurrencyIsoType currency) {
        instance.setCurrency(currency);
        return this;
    }

    public PriceBuilder mockWithBasicData(final LocalDateTime start, final LocalDateTime end) {
        return new PriceBuilder()
                .withId(1L)
                .withBrandId(1L)
                .withProductId(35455L)
                .withStartDate(start)
                .withEndDate(end)
                .withPriceListId(1L)
                .withPriority(true)
                .withCurrency(CurrencyIsoType.EUR);
    }

    public Price build() {
        return instance;
    }
}
