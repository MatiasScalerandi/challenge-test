package com.capitole.inditex.v1.service.impl;

import com.capitole.inditex.v1.builder.PriceBuilder;
import com.capitole.inditex.v1.builder.ProductItemBuilder;
import com.capitole.inditex.v1.builder.ProductRetrievalRequestBuilder;
import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.matcher.PriceMatcher;
import com.capitole.inditex.v1.matcher.ProductItemMatcher;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.service.component.PriceComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTest {

    @InjectMocks
    private PriceServiceImpl tested;

    @Mock
    private PriceComponent component;

    @Test
    void verify_toApiProductItem_whenBuildsProductWithGivenData() {
        //Given
        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .build();

        ProductRetrievalRequest retrievalRequest = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId("2020-06-15T16:00:00")
                .build();

        Mockito.doReturn(productItem)
                .when(component)
                .buildApiProductItem(retrievalRequest,
                        Collections.singletonList(productItem));

        //When
        ProductItem response = tested.toApiProductItem(retrievalRequest, Collections.singletonList(productItem));

        //Then
        Assertions.assertNotNull(response);
        assertThat(response, is(new ProductItemMatcher(productItem)));
    }

    @Test
    void verify_toApiProductItem_throwsIllegalArgumentException_whenProductWithGivenRequestIsNull() {
        //Given
        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .build();

        //When
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () ->
                tested.toApiProductItem(null, Collections.singletonList(productItem)));

        assertAll(
                //Then
                () -> assertEquals("'retrievalRequest' cannot be null", response.getMessage())
        );

    }

    @Test
    void verify_retrieveProductWithPriority_whenBuildsProductInDateRange() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 19, 30, 40);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .build();

        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .build();

        Mockito.doReturn(Optional.of(productItem))
                .when(component)
                .retrievePriceInDateRange(startDate, price);

        //When
        Optional<ProductItem> response = tested.retrieveProductWithPriority(startDate, price);

        //Then
        Assertions.assertNotNull(response);
        response.stream()
                .findFirst()
                .ifPresent(r ->
                        assertThat(r, is(new ProductItemMatcher(productItem))));
    }

    @Test
    void verify_retrieveProductWithPriority_throwsIllegalArgumentException_whenGivePriceIsNull() {
        //When
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () ->
                tested.retrieveProductWithPriority(LocalDateTime.now(),
                        null));

        assertAll(
                //Then
                () -> assertEquals("'price' cannot be null", response.getMessage())
        );
    }

    @Test
    void verify_retrievePricesByBrandIdAndProductId_whenBuildsProductWithGivenData() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 19, 30, 40);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .build();

        Mockito.doReturn(Collections.singletonList(price))
                .when(component)
                .retrieveFinalPriceByBrandIdAndProductId(1L, 35455L);

        //When
        List<Price> response = tested.retrievePricesByBrandIdAndProductId(1L,
                35455L);

        //Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(1, response.size()),
                () -> assertThat(response.get(0), is(new PriceMatcher(price)))
        );
    }

    @Test
    void verify_retrievePricesByBrandIdAndProductId_throwsIllegalArgumentException_whenBrandIdIsNull() {
        //When
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () ->
                tested.retrievePricesByBrandIdAndProductId(null,
                        35455L));

        assertAll(
                //Then
                () -> assertEquals("'brandId' cannot be null", response.getMessage())
        );
    }
}
