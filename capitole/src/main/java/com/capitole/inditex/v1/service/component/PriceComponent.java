package com.capitole.inditex.v1.service.component;

import com.capitole.inditex.v1.adapter.PriceAdapter;
import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class PriceComponent {

    private final PriceRepository repository;
    private final PriceAdapter adapter;

    public PriceComponent(PriceRepository repository, PriceAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    public List<Price> retrieveFinalPriceByBrandIdAndProductId(Long brandId, Long productId) {
        List<Price> priceList = repository.findByBrandIdAndProductId(brandId, productId);
        if (priceList.isEmpty()) {
            //not found
        }
        return priceList;

    }

    public ProductItem retrievePriceInDateRange(LocalDateTime requestDate, Price price) {
        boolean rateIsBetweenInDateRange = isBetween(requestDate, price.getStartDate(), price.getEndDate());
        List<Price> pricesInRange = new ArrayList<>();
        ProductItem productItem = new ProductItem();

        if (rateIsBetweenInDateRange) {
            pricesInRange.add(price);
            Double maxRateToApply = validateMaxRate(pricesInRange);
            productItem.setRateToApply(maxRateToApply);
        }

        return productItem;
    }

    public Double validateMaxRate(List<Price> pricesInRange) {
        double rate = 0.0;

        Price price = pricesInRange
                .stream()
                .max(Comparator.comparing(Price::getFinalPrice))
                .orElse(null);

        if (Objects.nonNull(price)) {
            rate = price.getFinalPrice().doubleValue();
        }
        return rate;
    }

    public ProductItem toApiProductItem(ProductRetrievalRequest retrievalRequest, List<ProductItem> items, ProductItem productItem) {
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
