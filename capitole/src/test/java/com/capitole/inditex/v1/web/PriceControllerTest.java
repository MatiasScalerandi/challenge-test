package com.capitole.inditex.v1.web;

import com.capitole.inditex.v1.adapter.PriceAdapter;
import com.capitole.inditex.v1.builder.PriceBuilder;
import com.capitole.inditex.v1.builder.ProductItemBuilder;
import com.capitole.inditex.v1.builder.ProductRetrievalRequestBuilder;
import com.capitole.inditex.v1.builder.ProductRetrievalResponseBuilder;
import com.capitole.inditex.v1.entity.Price;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.model.ProductRetrievalResponse;
import com.capitole.inditex.v1.service.PriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Unit test class for {@link PriceController}
 *
 * @author Matias Scalerandi
 */
@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    private static final String BASE_PATH = "/api/v1";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PriceService service;
    @MockBean
    private PriceAdapter adapter;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // -----------------------------
    // ping
    // -----------------------------

    @Test
    void verify_ping_whenAppIsUpReturnPong() {
        //Given
        //When
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/ping")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers
                            .status()
                            .isOk())
                    .andReturn();

        } catch (Exception e) {
            fail("Unexpected error " + e.getMessage());
        }

    }

    // -----------------------------
    // retrievePriceData
    // -----------------------------

    @Test
    void verify_retrievePriceData_whenGetPricesSuccessfully_test1() throws ParseException {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 10, 00, 00);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        String stringApplicationDate = "2020-06-14T10:00:00";
        String stringEndApplicationDate = "2021-07-14T10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date endApplicationDate = sdf.parse(stringEndApplicationDate);

        ProductRetrievalRequest request = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId(stringApplicationDate)
                .build();

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(38.95))
                .build();

        Price price2 = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(35.95))
                .build();

        List<Price> prices = new ArrayList<>();
        prices.add(price);
        prices.add(price2);

        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        ProductRetrievalResponse response = new ProductRetrievalResponseBuilder()
                .mockWithBasicData("2020-06-14T10:00:00", endApplicationDate)
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        Mockito.doReturn(prices)
                .when(service)
                .retrievePricesByBrandIdAndProductId(1L, 35455L);

        Mockito.doReturn(Optional.of(productItem))
                .when(service)
                .retrieveProductWithPriority(startDate, price);

        Mockito.doReturn(productItem)
                .when(service)
                .toApiProductItem(any(ProductRetrievalRequest.class), eq(Collections.singletonList(productItem)));

        Mockito.doReturn(response)
                .when(adapter)
                .toApiProductRetrievalResponse(productItem);
        //When
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH + "/price")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers
                            .status()
                            .isOk())
                    .andReturn();

            JSONAssert.assertEquals(readSampleFromFile("prices/retrieve_prices_successfully_test_1.json"),
                    result.getResponse().getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        } catch (Exception e) {
            fail("Unexpected error " + e.getMessage());
        }

    }

    @Test
    void verify_retrievePriceData_whenGetPricesSuccessfully_test2() throws ParseException {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 16, 00, 00);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        String stringApplicationDate = "2020-06-14T16:00:00";
        String stringEndApplicationDate = "2021-07-14T10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date endApplicationDate = sdf.parse(stringEndApplicationDate);

        ProductRetrievalRequest request = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId(stringApplicationDate)
                .build();

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(38.95))
                .build();

        Price price2 = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(35.95))
                .build();

        List<Price> prices = new ArrayList<>();
        prices.add(price);
        prices.add(price2);

        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        ProductRetrievalResponse response = new ProductRetrievalResponseBuilder()
                .mockWithBasicData("2020-06-14T16:00:00", endApplicationDate)
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        Mockito.doReturn(prices)
                .when(service)
                .retrievePricesByBrandIdAndProductId(1L, 35455L);

        Mockito.doReturn(Optional.of(productItem))
                .when(service)
                .retrieveProductWithPriority(startDate, price);

        Mockito.doReturn(productItem)
                .when(service)
                .toApiProductItem(any(ProductRetrievalRequest.class), eq(Collections.singletonList(productItem)));

        Mockito.doReturn(response)
                .when(adapter)
                .toApiProductRetrievalResponse(productItem);
        //When
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH + "/price")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers
                            .status()
                            .isOk())
                    .andReturn();

            JSONAssert.assertEquals(readSampleFromFile("prices/retrieve_prices_successfully_test_2.json"),
                    result.getResponse().getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        } catch (Exception e) {
            fail("Unexpected error " + e.getMessage());
        }

    }

    @Test
    void verify_retrievePriceData_whenGetPricesSuccessfully_test3() throws ParseException {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 14, 21, 00, 00);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        String stringApplicationDate = "2020-06-14T21:00:00";
        String stringEndApplicationDate = "2021-07-14T10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date endApplicationDate = sdf.parse(stringEndApplicationDate);

        ProductRetrievalRequest request = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId(stringApplicationDate)
                .build();

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(38.95))
                .build();

        Price price2 = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(35.95))
                .build();

        List<Price> prices = new ArrayList<>();
        prices.add(price);
        prices.add(price2);

        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        ProductRetrievalResponse response = new ProductRetrievalResponseBuilder()
                .mockWithBasicData("2020-06-14T21:00:00", endApplicationDate)
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        Mockito.doReturn(prices)
                .when(service)
                .retrievePricesByBrandIdAndProductId(1L, 35455L);

        Mockito.doReturn(Optional.of(productItem))
                .when(service)
                .retrieveProductWithPriority(startDate, price);

        Mockito.doReturn(productItem)
                .when(service)
                .toApiProductItem(any(ProductRetrievalRequest.class), eq(Collections.singletonList(productItem)));

        Mockito.doReturn(response)
                .when(adapter)
                .toApiProductRetrievalResponse(productItem);
        //When
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH + "/price")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers
                            .status()
                            .isOk())
                    .andReturn();

            JSONAssert.assertEquals(readSampleFromFile("prices/retrieve_prices_successfully_test_3.json"),
                    result.getResponse().getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        } catch (Exception e) {
            fail("Unexpected error " + e.getMessage());
        }

    }

    @Test
    void verify_retrievePriceData_whenGetPricesSuccessfully_test4() throws ParseException {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 15, 10, 00, 00);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        String stringApplicationDate = "2020-06-15T10:00:00";
        String stringEndApplicationDate = "2021-07-14T10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date endApplicationDate = sdf.parse(stringEndApplicationDate);

        ProductRetrievalRequest request = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId(stringApplicationDate)
                .build();

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(38.95))
                .build();

        Price price2 = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(35.95))
                .build();

        List<Price> prices = new ArrayList<>();
        prices.add(price);
        prices.add(price2);

        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        ProductRetrievalResponse response = new ProductRetrievalResponseBuilder()
                .mockWithBasicData("2020-06-15T10:00:00", endApplicationDate)
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        Mockito.doReturn(prices)
                .when(service)
                .retrievePricesByBrandIdAndProductId(1L, 35455L);

        Mockito.doReturn(Optional.of(productItem))
                .when(service)
                .retrieveProductWithPriority(startDate, price);

        Mockito.doReturn(productItem)
                .when(service)
                .toApiProductItem(any(ProductRetrievalRequest.class), eq(Collections.singletonList(productItem)));

        Mockito.doReturn(response)
                .when(adapter)
                .toApiProductRetrievalResponse(productItem);
        //When
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH + "/price")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers
                            .status()
                            .isOk())
                    .andReturn();

            JSONAssert.assertEquals(readSampleFromFile("prices/retrieve_prices_successfully_test_4.json"),
                    result.getResponse().getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        } catch (Exception e) {
            fail("Unexpected error " + e.getMessage());
        }

    }

    @Test
    void verify_retrievePriceData_whenGetPricesSuccessfully_test5() throws ParseException {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JUNE, 16, 21, 00, 00);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JUNE, 30, 19, 30, 40);

        String stringApplicationDate = "2020-06-16T21:00:00";
        String stringEndApplicationDate = "2021-07-14T10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date endApplicationDate = sdf.parse(stringEndApplicationDate);

        ProductRetrievalRequest request = new ProductRetrievalRequestBuilder()
                .mockWithBrandIdAndProductId(stringApplicationDate)
                .build();

        Price price = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(38.95))
                .build();

        Price price2 = new PriceBuilder()
                .mockWithBasicData(startDate, endDate)
                .withFinalPrice(BigDecimal.valueOf(35.95))
                .build();

        List<Price> prices = new ArrayList<>();
        prices.add(price);
        prices.add(price2);

        ProductItem productItem = new ProductItemBuilder()
                .mockWithBasicData("2020-06-15T16:00:00", new Date())
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        ProductRetrievalResponse response = new ProductRetrievalResponseBuilder()
                .mockWithBasicData("2020-06-16T21:00:00", endApplicationDate)
                .withRateToApply(38.95)
                .withFinalPrice(38.95)
                .build();

        Mockito.doReturn(prices)
                .when(service)
                .retrievePricesByBrandIdAndProductId(1L, 35455L);

        Mockito.doReturn(Optional.of(productItem))
                .when(service)
                .retrieveProductWithPriority(startDate, price);

        Mockito.doReturn(productItem)
                .when(service)
                .toApiProductItem(any(ProductRetrievalRequest.class), eq(Collections.singletonList(productItem)));

        Mockito.doReturn(response)
                .when(adapter)
                .toApiProductRetrievalResponse(productItem);
        //When
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH + "/price")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers
                            .status()
                            .isOk())
                    .andReturn();

            JSONAssert.assertEquals(readSampleFromFile("prices/retrieve_prices_successfully_test_5.json"),
                    result.getResponse().getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        } catch (Exception e) {
            fail("Unexpected error " + e.getMessage());
        }

    }

    private String readSampleFromFile(String filePath) throws IOException {
        try (InputStream expectedResponseAsStream = PriceControllerTest.class.getClassLoader()
                .getResourceAsStream("samples/" + filePath)) {
            return StreamUtils.copyToString(expectedResponseAsStream, StandardCharsets.UTF_8);
        }
    }
}
