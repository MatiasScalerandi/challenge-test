package com.capitole.inditex.v1.service.component;

import com.capitole.inditex.v1.adapter.PriceAdapter;
import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.repository.PriceRepository;
import com.capitole.inditex.v1.service.exception.PriceNotFoundException;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class PriceComponent {
    private final static Logger LOGGER = LoggerFactory.getLogger(PriceComponent.class);
    private final PriceRepository repository;
    private final PriceAdapter adapter;

    public PriceComponent(PriceRepository repository, PriceAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    public List<Price> retrieveFinalPriceByBrandIdAndProductId(Long brandId, Long productId) {
        Preconditions.checkArgument(Objects.nonNull(brandId));
        Preconditions.checkArgument(Objects.nonNull(productId));

        List<Price> priceList = repository.findByBrandIdAndProductId(brandId, productId);
        if (priceList.isEmpty()) {
            LOGGER.error("Price it doesn't exists");
            String message = String.format("The price with brand with id %d and product with id %d doesn't exists", brandId, productId);
            throw new PriceNotFoundException(message);
        }
        return priceList;

    }

    public Optional<ProductItem> retrievePriceInDateRange(LocalDateTime requestDate, Price price) {
        Preconditions.checkArgument(Objects.nonNull(price));

        boolean rateIsBetweenInDateRange = isBetween(requestDate, price.getStartDate(), price.getEndDate());
        ProductItem productItem = new ProductItem();

        LOGGER.debug("rateIsBetweenInDateRange ? = '{}'", rateIsBetweenInDateRange);
        if (rateIsBetweenInDateRange) {
            buildProductItem(price, productItem);
        }

        return Optional.of(productItem);
    }

    private void buildProductItem(Price price, ProductItem productItem) {
        List<Price> pricesInRange = new ArrayList<>();
        pricesInRange.add(price);
        Double maxRateToApply = validateMaxRate(pricesInRange);
        productItem.setRateToApply(maxRateToApply);
    }

    public Double validateMaxRate(List<Price> pricesInRange) {
        var rate = 0.0;
        Price price = getPrice(pricesInRange);

        if (Objects.nonNull(price)) {
            rate = price.getFinalPrice().doubleValue();
        }
        return rate;
    }

    private static Price getPrice(List<Price> pricesInRange) {
        return pricesInRange
                .stream()
                .max(Comparator.comparing(Price::getFinalPrice))
                .orElse(null);
    }

    public ProductItem buildApiProductItem(ProductRetrievalRequest retrievalRequest, List<ProductItem> items, ProductItem productItem) {
        if (!items.isEmpty()) {
            double maxRateToApply = getMaxRate(items);
            productItem = adapter.requestToProductItem(retrievalRequest);
            productItem.setRateToApply(maxRateToApply);
            productItem.setApplicationEndDate((new Date()));
            productItem.setFinalPrice(maxRateToApply);
        }
        return productItem;
    }

    private static double getMaxRate(List<ProductItem> items) {
        return items
                .stream()
                .filter(Objects::nonNull)
                .map(ProductItem::getRateToApply)
                .filter(Objects::nonNull)
                .max(Double::compare)
                .orElse(0.0);
    }

    private static <C extends Comparable<? super C>> boolean isBetween(C value, C start, C end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) < 0;
    }
}
