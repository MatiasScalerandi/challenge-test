package com.capitole.inditex.v1.service;

import com.capitole.inditex.v1.entity.Price;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PriceService {

    Optional<Price> retrieveFinalPriceByBrandIdAndProductId(Long brandId, Long productId);
    boolean isPriority(Date applicationStartDate, Price dbPrice);
    List<Price> retrieveAllPricesByBrandId(Long brandId);

}
