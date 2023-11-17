package com.capitole.inditex.v1.web;

import com.capitole.inditex.v1.adapter.PriceAdapter;
import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.service.PriceService;
import com.capitole.inditex.v1.util.PriceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
public class PriceController {

    private final PriceService service;
    private final PriceAdapter adapter;

    public PriceController(PriceService service, PriceAdapter adapter) {
        this.service = service;
        this.adapter = adapter;
    }

    @GetMapping(value = "/ping",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @PostMapping(value = "/price",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> retrievePriceData(final @Valid @RequestBody ProductRetrievalRequest retrievalRequest,
                                                    final BindingResult errors) throws ParseException {
        if (errors.hasErrors()) {
            //throw exception
        }

        List<ProductItem> items = new ArrayList<>();
        ProductItem productItem = new ProductItem();

        Date date = PriceUtils.parseStringToDateType(retrievalRequest);
        List<Price> prices = getPriceByBrandIdAndProductIdOrThrowException(retrievalRequest.getBrandId(),
                retrievalRequest.getProductId(), date);

        LocalDateTime localDateTimeRequest = PriceUtils.convertToLocalDateTimeViaInstant(date);

        prices.forEach(p -> items.add(service.retrieveProductWithPriority(localDateTimeRequest, p)));

        productItem = service.toApiProductItem(retrievalRequest, items, productItem);

        return new ResponseEntity<>(adapter.toApiProductRetrievalResponse(productItem), HttpStatus.OK);
    }


    private List<Price> getPriceByBrandIdAndProductIdOrThrowException(Long brandId, Long productId, Date startDate) {
        return service.retrievePricesByBrandIdAndProductId(brandId, productId);
    }


}
