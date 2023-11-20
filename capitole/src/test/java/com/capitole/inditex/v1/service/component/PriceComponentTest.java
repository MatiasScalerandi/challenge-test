package com.capitole.inditex.v1.service.component;

import com.capitole.inditex.v1.adapter.PriceAdapter;
import com.capitole.inditex.v1.builder.PriceBuilder;
import com.capitole.inditex.v1.builder.ProductItemBuilder;
import com.capitole.inditex.v1.builder.ProductRetrievalRequestBuilder;
import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.matcher.PriceMatcher;
import com.capitole.inditex.v1.matcher.ProductItemMatcher;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.repository.PriceRepository;
import com.capitole.inditex.v1.service.exception.PriceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PriceComponentTest {

    @InjectMocks
    private PriceComponent tested;

    @Mock
    private PriceAdapter adapter;
    @Mock
    private PriceRepository repository;

    @Test
    void verify_validateMaxRate_whenGetMaxRate() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 19, 30, 40);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .build();

        price.setFinalPrice(BigDecimal.valueOf(35.0));

        Double response = tested.validateMaxRate(Collections.singletonList(price));
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(35.0, response.doubleValue())
        );
    }


    @Test
    void verify_retrieveFinalPriceByBrandIdAndProductId_whenGetPricesSuccessfully() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 19, 30, 40);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);


        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .build();

        Mockito.doReturn(Collections.singletonList(price))
                .when(repository)
                .findByBrandIdAndProductId(1L, 35455L);

        //When
        List<Price> response = tested.retrieveFinalPriceByBrandIdAndProductId(1L,
                35455L);

        assertAll(
                //Then
                () -> assertEquals(1, response.size()),
                () -> assertThat(response.get(0), is(new PriceMatcher(price)))
        );
    }

    @Test
    void verify_retrieveFinalPriceByBrandIdAndProductId_throwsPriceNotFoundException() {
        //Given
        Mockito.doReturn(Collections.EMPTY_LIST)
                .when(repository)
                .findByBrandIdAndProductId(2L, 35455L);

        //When
        PriceNotFoundException response = assertThrows(PriceNotFoundException.class, () ->
                tested.retrieveFinalPriceByBrandIdAndProductId(2L,
                        35455L));

        assertAll(
                //Then
                () -> assertEquals("The price with brand with id 2 and product with id 35455 doesn't exists", response.getMessage())
        );
    }

    @Test
    void verify_buildApiProductItem_whenListPricesAreEmpty() {
        //Given
        ProductRetrievalRequest productRetrievalRequest = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId("2020-06-14T10:00:00")
                .build();

        //When
        ProductItem response = tested.buildApiProductItem(productRetrievalRequest,
                new ArrayList<>());

        assertAll(
                //Then
                () -> assertNotNull(response),
                () -> assertThat(response, is(new ProductItemMatcher(new ProductItem())))
        );
    }

    @Test
    void verify_buildApiProductItem_whenReturnProductItemWithGivenData() {
        //Given
        ProductRetrievalRequest productRetrievalRequest = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId("2020-06-14T10:00:00")
                .build();

        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .withRateToApply(38.50)
                .build();

        Mockito.doReturn(productItem)
                .when(adapter)
                .requestToProductItem(productRetrievalRequest);

        //When
        ProductItem response = tested.buildApiProductItem(productRetrievalRequest,
                Collections.singletonList(productItem));

        assertAll(
                //Then
                () -> assertNotNull(response),
                () -> assertThat(response, is(new ProductItemMatcher(productItem)))
        );
    }

    @Test
    void verify_retrievePriceInDateRange_whenPriceIsBetweenInDateRange() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 19, 30, 40);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(38.50))
                .build();

        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .withRateToApply(38.50)
                .build();

        //When
        Optional<ProductItem> response = tested.retrievePriceInDateRange(startDate, price);

        assertAll(
                //Then
                () -> assertNotNull(response),
                () -> response.stream()
                        .findFirst()
                        .ifPresent(r ->
                                assertThat(r.getRateToApply(), is(productItem.getRateToApply())))
        );
    }

    @Test
    void verify_retrievePriceInDateRange_whenPriceIsNotBetweenInDateRange() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 19, 30, 40);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(38.50))
                .build();

        //When
        Optional<ProductItem> response = tested.retrievePriceInDateRange(LocalDateTime.now(), price);

        assertAll(
                //Then
                () -> assertNotNull(response),
                () -> response.stream()
                        .findFirst()
                        .ifPresent(r ->
                                assertThat(r, is(new ProductItemMatcher(new ProductItem()))))
        );
    }
}
