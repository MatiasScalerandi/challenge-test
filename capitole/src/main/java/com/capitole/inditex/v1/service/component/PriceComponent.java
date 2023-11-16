package com.capitole.inditex.v1.service.component;

import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class PriceComponent {

    private final PriceRepository repository;

    public PriceComponent(PriceRepository repository) {
        this.repository = repository;
    }

    public Optional<Price> retrieveFinalPriceByBrandIdAndProductId(Long brandId, Long productId) {
        Price price = repository.findByBrandIdAndProductId(brandId, productId);
        return Optional.ofNullable(price);
    }

    public boolean isPriority(Date applicationStartDate, Price dbPrice) {
        return false;
    }


    public List<Price> retrieveAllPricesByBrandId(Long brandId) {
        return repository.findAllByBrandId(brandId);
    }
}
