package com.capitole.inditex.v1.service.impl;

import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.service.PriceService;
import com.capitole.inditex.v1.service.component.PriceComponent;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceComponent component;

    public PriceServiceImpl(PriceComponent component) {
        this.component = component;
    }

    @Override
    public Optional<Price> retrieveFinalPriceByBrandIdAndProductId(Long brandId, Long productId) {
        return component.retrieveFinalPriceByBrandIdAndProductId(brandId, productId);
    }

    @Override
    public boolean isPriority(Date applicationStartDate, Price dbPrice) {
        return component.isPriority(applicationStartDate, dbPrice);
    }

    @Override
    public List<Price> retrieveAllPricesByBrandId(Long brandId) {
        return component.retrieveAllPricesByBrandId(brandId);
    }
}
