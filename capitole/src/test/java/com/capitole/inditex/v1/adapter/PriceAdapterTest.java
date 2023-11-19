package com.capitole.inditex.v1.adapter;

import com.capitole.inditex.v1.builder.ProductItemBuilder;
import com.capitole.inditex.v1.builder.ProductRetrievalRequestBuilder;
import com.capitole.inditex.v1.builder.ProductRetrievalResponseBuilder;
import com.capitole.inditex.v1.mapper.ProductMapper;
import com.capitole.inditex.v1.matcher.ProductItemMatcher;
import com.capitole.inditex.v1.matcher.ProductRetrievalResponseMatcher;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.model.ProductRetrievalResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class PriceAdapterTest {
    @InjectMocks
    private PriceAdapter tested;

    @Mock
    private ProductMapper productMapper;

    private ProductItem productItem;
    private ProductRetrievalResponse expected;
    private ProductRetrievalRequest request;

    @BeforeEach
    void init() {
        productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .build();

        expected = new ProductRetrievalResponseBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .build();


        request = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId(new Date())
                .build();
    }

    @Test
    void verify_toApiProductRetrievalResponse_whenAdapterBuildsSuccessfully() {
        ProductRetrievalResponse response = tested.toApiProductRetrievalResponse(productItem);
        Assertions.assertNotNull(response);
        assertThat(response, is(new ProductRetrievalResponseMatcher(expected)));
    }

    @Test
    void verify_toApiProductRetrievalResponse_whenRequestIsNull() {
        ProductRetrievalResponse response = tested.toApiProductRetrievalResponse(null);
        Assertions.assertNotNull(response);
        assertThat(response, is(new ProductRetrievalResponseMatcher(new ProductRetrievalResponse())));
    }

    @Test
    void verify_requestToProductItem_whenAdapterBuildsSuccessfully() {
        //Given
        Mockito.doReturn(productItem)
                .when(productMapper)
                .requestToProductItem(request);

        //When
        ProductItem response = tested.requestToProductItem(request);

        //Then
        Assertions.assertNotNull(response);
        assertThat(response, is(new ProductItemMatcher(productItem)));
    }

    @Test
    void verify_requestToProductItem_whenAdapterRequestIsNull() {
        //When
        ProductItem response = tested.requestToProductItem(null);

        //Then
        Assertions.assertNotNull(response);
        assertThat(response, is(new ProductItemMatcher(new ProductItem())));
    }

}
